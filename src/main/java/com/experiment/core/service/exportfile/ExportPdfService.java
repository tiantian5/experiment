package com.experiment.core.service.exportfile;

import com.google.common.collect.Maps;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import com.tuya.csm.order.ingetration.file.CetusStorageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * @author wusongsong
 * @date 2022/8/3
 */
@Slf4j
@Service
public class ExportPdfService {
    @Autowired
    private CetusStorageService cetusStorageService;

    public String generatePdfFile(String htmlContent) throws Exception{
        org.jsoup.nodes.Document document = Jsoup.parse(htmlContent);
        Element style = document.head().select("style").first();

        InputSource source = new InputSource(
                new StringReader(
                        style.html()));
        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
        CSSRuleList rules = sheet.getCssRules();
        Map<String, Map<String, String>> cssMap = Maps.newHashMap();

        for (int i = 0; i < rules.getLength(); i++) {
            final CSSRule rule = rules.item(i);
            String cssText = rule.getCssText();
            int left = cssText.indexOf("{");
            int right = cssText.indexOf("}");
            String key = cssText.substring(0, left).trim();
            String attrs = cssText.substring(left + 1, right).trim();

            String[] split = attrs.split(";");
            Map<String, String> attrMap = Maps.newHashMap();
            cssMap.put(key, attrMap);
            for (String s : split) {
                String[] itemSplit = s.split(":");
                attrMap.put(itemSplit[0].trim(), itemSplit[1].trim());
            }
        }

        Element body = document.body();
        Element table = body.getElementsByTag("table").get(0);
        Element colgroup = table.getElementsByTag("colgroup").get(0);
        Elements trs = table.getElementsByTag("tbody").get(0).getElementsByTag("tr");

        //内容填充-格式填充
        byte[] bytes = generatePdf(cssMap, colgroup, trs);
        String cloudKey = cetusStorageService.uploadFile(bytes, System.currentTimeMillis() + ".pdf", "inspection_result");
        return cetusStorageService.getDownloadUrl(cloudKey);
    }

    private byte[] generatePdf(Map<String, Map<String, String>> cssMap, Element colgroup, Elements trs) throws Exception{
        Elements cols = colgroup.getElementsByTag("col");
        int size = cols.size();
        float[] widthArray = new float[size];
        int i = 0;
        for (Element col : cols) {
            String className = col.attr("class");
            Map<String, String> attrMap = cssMap.get("." + className);
            String width = attrMap.get("width");
            float px = Float.parseFloat(width.replaceAll("px", ""));
            widthArray[i++] = px;
        }
        Map<String, String> body = cssMap.get("body");
        float marginLeft = Float.parseFloat(body.get("margin-left").replaceAll("px", ""));
        float marginRight = Float.parseFloat(body.get("margin-right").replaceAll("px", ""));
        float marginTop = Float.parseFloat(body.get("margin-top").replaceAll("px", ""));
        float marginBottom = Float.parseFloat(body.get("margin-bottom").replaceAll("px", ""));

        //创建文件
        Document document = new Document(PageSize.A4.rotate());

        //建立一个书写器
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        writer.setStrictImageSequence(true);
        //打开文件
        document.open();
        document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
        //段落行间距
        PdfPTable table = new PdfPTable(widthArray.length);
        table.setWidthPercentage(100);
        List<PdfPRow> allRowList = table.getRows();

        int rowIndex = 0;
        Map<Integer, Integer> col2RowMax = Maps.newHashMap();
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            PdfPCell[] pdfPCells = new PdfPCell[widthArray.length];
            PdfPRow row = new PdfPRow(pdfPCells);
            int colIndex = 0;
            for (Element td : tds) {
                String colspanStr = td.attr("colspan");
                String rowspanStr = td.attr("rowspan");
                int colspan = 1;
                int rowspan = 1;
                if (StringUtils.isNotBlank(colspanStr)) {
                    colspan = Integer.parseInt(colspanStr);
                }
                if (StringUtils.isNotBlank(rowspanStr)) {
                    rowspan = Integer.parseInt(rowspanStr);
                }
                while (colIndex < 10000) {
                    Integer maxRow = col2RowMax.get(colIndex);
                    if (maxRow == null) {
                        break;
                    }
                    if (rowIndex > maxRow) {
                        break;
                    }
                    colIndex++;
                }
                if (colIndex >= pdfPCells.length) {
                    break;
                }
                if (rowIndex == 0) {
                    generateHeaderCell(pdfPCells, row, td, rowIndex, rowspan, colIndex, colspan);
                } else {
                    generateCell(pdfPCells, row, td, rowIndex, rowspan, colIndex, colspan, cssMap);
                }
                if (rowspan > 1) {
                    col2RowMax.put(colIndex, rowIndex + rowspan - 1);
                }
                colIndex += colspan;
            }
            allRowList.add(row);
            rowIndex++;
        }
        document.add(table);
        document.close();
        writer.close();
        return out.toByteArray();
    }

    private static void generateCell(PdfPCell[] pdfPCells, PdfPRow row, Element td, int rowIndex, int rowspan, int colIndex, int colspan, Map<String, Map<String, String>> cssMap) {
        String styleKey = td.attr("class");

        Map<String, String> styleMap = cssMap.get("." + styleKey);
        String vertical = styleMap.get("vertical-align");
        String textAlign = styleMap.get("text-align");
        String fontSize = styleMap.get("font-size").replaceAll("px", "");
        String bold = styleMap.get("font-weight");
        String paddingTop = styleMap.get("padding-top").replaceAll("px", "");
        String paddingBottom = styleMap.get("padding-bottom").replaceAll("px", "");
        String color = null;
        Elements spans = td.getElementsByTag("span");
        if (CollectionUtils.isNotEmpty(spans)) {
            Element span = spans.get(0);
            String spanClass = span.attr("class");
            if (StringUtils.isNotBlank(spanClass) && cssMap.containsKey("." + spanClass)) {
                Map<String, String> spanStyleMap = cssMap.get("." + spanClass);
                if (spanStyleMap.containsKey("font-size")) {
                    fontSize = spanStyleMap.get("font-size").replaceAll("px", "");
                }
                if (spanStyleMap.containsKey("font-weight")) {
                    bold = spanStyleMap.get("font-weight");
                }
                if (spanStyleMap.containsKey("color")) {
                    color = spanStyleMap.get("color").replaceAll("#", "");
                }
            }
        }
        Font font = FontFactory.getFont("classpath:ttf/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, Integer.parseInt(fontSize), StringUtils.equals(bold, "bold") ? Font.BOLD : Font.NORMAL);
        if (StringUtils.isNotBlank(color)) {
            String rgb = color.replaceAll("rgb", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
            String[] split = rgb.split(",");
            int red = Integer.parseInt(split[0]);
            int green = Integer.parseInt(split[1]);
            int blue = Integer.parseInt(split[2]);
            font.setColor(new BaseColor(red, green, blue));
        }
        PdfPCell cell = new PdfPCell(new Paragraph(td.text(), font));
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        if (rowspan > 1) {
            cell.setRowspan(rowspan);
        }

        if (StringUtils.equals(vertical, "middle")) {
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        }
        if (StringUtils.equals(textAlign, "center")) {
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        } else {
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        }
        if (StringUtils.isNotBlank(paddingTop)) {
            cell.setPaddingTop(Integer.parseInt(paddingTop));
        }
        if (StringUtils.isNotBlank(paddingBottom)) {
            cell.setPaddingBottom(Integer.parseInt(paddingBottom));
        }

        pdfPCells[colIndex] = cell;
    }

    private void generateHeaderCell(PdfPCell[] pdfPCells, PdfPRow row, Element td, int rowIndex, int rowspan, int colIndex, int colspan) throws Exception{
        PdfPCell cell = new PdfPCell();
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        if (rowspan > 1) {
            cell.setRowspan(rowspan);
        }
        PdfPTable innerTable = new PdfPTable(3);
        innerTable.setWidthPercentage(100);
        innerTable.setWidths(new float[]{1F,1F,1F});
        innerTable.setPaddingTop(0);
        innerTable.setSpacingAfter(0f);
        innerTable.setSpacingAfter(0f);
        Element imgEle = td.getElementsByTag("div").get(0).getElementsByTag("img").get(0);


        BufferedImage targetImage = ImageIO.read(cetusStorageService.downloadToInputStream(imgEle.attr("src")));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(targetImage, "png", out);
        Image image = Image.getInstance(out.toByteArray());
        image.scaleAbsoluteHeight(52f);
        image.scaleAbsoluteWidth(100f);



        List<PdfPRow> innerRowList = innerTable.getRows();
        PdfPRow innerRow = new PdfPRow(new PdfPCell[3]);
        innerRowList.add(innerRow);
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setPaddingTop(8);
        imageCell.setPaddingBottom(8);
        imageCell.setPaddingLeft(10);

        innerRow.getCells()[0] = imageCell;

        Font font1 = FontFactory.getFont("classpath:ttf/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 20, Font.BOLD);
        Font font2 = FontFactory.getFont("classpath:ttf/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 16, Font.BOLD);

        Element span1 = td.getElementsByTag("span").get(0);
        Element span3 = td.getElementsByTag("span").get(2);

        Paragraph paragraph1 = new Paragraph(span1.text());
        paragraph1.setFont(font1);
        paragraph1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        Paragraph paragraph2 = new Paragraph(span3.text());
        paragraph2.setFont(font2);
        PdfPCell innerCell2 = new PdfPCell();
        innerCell2.addElement(paragraph1);
        paragraph2.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

        innerCell2.addElement(paragraph2);
//        innerCell2.setLeading();
        innerCell2.setPadding(0);
        innerCell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        innerCell2.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        imageCell.setBorder(0);
        innerCell2.setBorder(0);

        innerRow.getCells()[1] = innerCell2;
        PdfPCell innerCell3 = new PdfPCell(new Paragraph(""));
        innerCell3.setPadding(0);
        innerRow.getCells()[2] = innerCell3;
        innerRow.getCells()[2].setBorder(0);

        cell.addElement(innerTable);
        pdfPCells[colIndex] = cell;
    }

    public static String getTemplate(String path, Object map) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template temp = new Template("inspection_export", new BufferedReader(new InputStreamReader(new FileInputStream(path))), cfg);
        StringWriter stringWriter = new StringWriter();
        temp.process(map, stringWriter);
        return stringWriter.toString();
    }
}
