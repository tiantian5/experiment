//package com.experiment.core.service.exportfile;
//
//import com.google.common.collect.Maps;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import com.steadystate.css.parser.CSSOMParser;
//import com.steadystate.css.parser.SACParserCSS3;
//import com.tuya.csm.order.common.util.BeanCopyUtil;
//import com.tuya.csm.order.ingetration.file.CetusStorageService;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateExceptionHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.compress.utils.Lists;
//import org.apache.commons.lang3.StringUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.w3c.css.sac.InputSource;
//import org.w3c.dom.css.CSSRule;
//import org.w3c.dom.css.CSSRuleList;
//import org.w3c.dom.css.CSSStyleSheet;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wusongsong
// * @date 2022/8/3
// */
//@Slf4j
//@Service
//public class ExportPdfService {
//    @Autowired
//    private CetusStorageService cetusStorageService;
//
//    public String generatePdfFile(String htmlContent) throws Exception{
//        org.jsoup.nodes.Document document = Jsoup.parse(htmlContent);
//        Element style = document.head().select("style").first();
//
//        InputSource source = new InputSource(
//                new StringReader(
//                        style.html()));
//        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
//        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
//        CSSRuleList rules = sheet.getCssRules();
//        Map<String, Map<String, String>> cssMap = Maps.newHashMap();
//
//        for (int i = 0; i < rules.getLength(); i++) {
//            final CSSRule rule = rules.item(i);
//            String cssText = rule.getCssText();
//            int left = cssText.indexOf("{");
//            int right = cssText.indexOf("}");
//            String key = cssText.substring(0, left).trim();
//            String attrs = cssText.substring(left + 1, right).trim();
//
//            String[] split = attrs.split(";");
//            Map<String, String> attrMap = Maps.newHashMap();
//            cssMap.put(key, attrMap);
//            for (String s : split) {
//                String[] itemSplit = s.split(":");
//                attrMap.put(itemSplit[0].trim(), itemSplit[1].trim());
//            }
//        }
//
//        Element body = document.body();
//        Element table = body.getElementsByTag("table").get(0);
//        Element colgroup = table.getElementsByTag("colgroup").get(0);
//        Elements trs = table.getElementsByTag("tbody").get(0).getElementsByTag("tr");
//
//        //内容填充-格式填充
//        byte[] bytes = generatePdf(cssMap, colgroup, trs);
//        String cloudKey = cetusStorageService.uploadFile(bytes, System.currentTimeMillis() + ".pdf", "inspection_result");
//        return cetusStorageService.getDownloadUrl(cloudKey);
//    }
//
//    private byte[] generatePdf(Map<String, Map<String, String>> cssMap, Element colgroup, Elements trs) throws Exception{
//        Elements cols = colgroup.getElementsByTag("col");
//        int size = cols.size();
//        float[] widthArray = new float[size];
//        int i = 0;
//        for (Element col : cols) {
//            String className = col.attr("class");
//            Map<String, String> attrMap = cssMap.get("." + className);
//            String width = attrMap.get("width");
//            float px = Float.parseFloat(width.replaceAll("px", ""));
//            widthArray[i++] = px;
//        }
//        Map<String, String> body = cssMap.get("body");
//        float marginLeft = Float.parseFloat(body.get("margin-left").replaceAll("px", ""));
//        float marginRight = Float.parseFloat(body.get("margin-right").replaceAll("px", ""));
//        float marginTop = Float.parseFloat(body.get("margin-top").replaceAll("px", ""));
//        float marginBottom = Float.parseFloat(body.get("margin-bottom").replaceAll("px", ""));
//
//        //创建文件
//        Document document = new Document(PageSize.A4.rotate());
//
//        //建立一个书写器
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        PdfWriter writer = PdfWriter.getInstance(document, out);
//        writer.setStrictImageSequence(true);
//        //打开文件
//        document.open();
//        document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
//        //段落行间距
//        PdfPTable table = new PdfPTable(widthArray.length);
//        table.setWidthPercentage(100);
//        List<PdfPRow> allRowList = table.getRows();
//
//        int rowIndex = 0;
//        Map<Integer, Integer> col2RowMax = Maps.newHashMap();
//        for (Element tr : trs) {
//            Elements tds = tr.getElementsByTag("td");
//            PdfPCell[] pdfPCells = new PdfPCell[widthArray.length];
//            PdfPRow row = new PdfPRow(pdfPCells);
//            int colIndex = 0;
//            for (Element td : tds) {
//                String colspanStr = td.attr("colspan");
//                String rowspanStr = td.attr("rowspan");
//                int colspan = 1;
//                int rowspan = 1;
//                if (StringUtils.isNotBlank(colspanStr)) {
//                    colspan = Integer.parseInt(colspanStr);
//                }
//                if (StringUtils.isNotBlank(rowspanStr)) {
//                    rowspan = Integer.parseInt(rowspanStr);
//                }
//                while (colIndex < 10000) {
//                    Integer maxRow = col2RowMax.get(colIndex);
//                    if (maxRow == null) {
//                        break;
//                    }
//                    if (rowIndex > maxRow) {
//                        break;
//                    }
//                    colIndex++;
//                }
//                if (colIndex >= pdfPCells.length) {
//                    break;
//                }
//                if (rowIndex == 0) {
//                    generateHeaderCell(pdfPCells, row, td, rowIndex, rowspan, colIndex, colspan);
//                } else {
//                    generateCell(pdfPCells, row, td, rowIndex, rowspan, colIndex, colspan, cssMap);
//                }
//                if (rowspan > 1) {
//                    col2RowMax.put(colIndex, rowIndex + rowspan - 1);
//                }
//                colIndex += colspan;
//            }
//            allRowList.add(row);
//            rowIndex++;
//        }
//        document.add(table);
//        document.close();
//        writer.close();
//        return out.toByteArray();
//    }
//
//    private static void generateCell(PdfPCell[] pdfPCells, PdfPRow row, Element td, int rowIndex, int rowspan, int colIndex, int colspan, Map<String, Map<String, String>> cssMap) {
//        String styleKey = td.attr("class");
//
//        CssStyleModel baseStyleModel = generateCssModel(cssMap, "." + styleKey);
//        Elements spans = td.getElementsByTag("span");
//        List<com.itextpdf.text.Element> elementList = Lists.newArrayList();
//        if (CollectionUtils.isNotEmpty(spans)) {
//            for (Element span : spans) {
//                CssStyleModel itemStyle = BeanCopyUtil.copyWithId(baseStyleModel, CssStyleModel.class);
//                String spanClass = span.attr("class");
//                updateCssModel(itemStyle, cssMap, "." + spanClass);
//
//                Font itemFont = createFontByCss(itemStyle);
//
//                String[] split = span.text().split("\\|");
//                for (String line : split) {
//                    Paragraph paragraph = new Paragraph(line, itemFont);
//                    if (StringUtils.equals(itemStyle.getHorizontalAlign(), "center")) {
//                        paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//                    } else {
//                        paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//                    }
//                    elementList.add(paragraph);
//                }
//            }
//        } else {
//            Font font = createFontByCss(baseStyleModel);
//            elementList.add(new Paragraph(td.text(), font));
//        }
//
//        PdfPCell cell = new PdfPCell();
//        for (com.itextpdf.text.Element element : elementList) {
//            cell.addElement(element);
//        }
//        if (colspan > 1) {
//            cell.setColspan(colspan);
//        }
//        if (rowspan > 1) {
//            cell.setRowspan(rowspan);
//        }
//
//        if (StringUtils.equals(baseStyleModel.getVerticalAlign(), "middle")) {
//            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        }
//        if (StringUtils.equals(baseStyleModel.getHorizontalAlign(), "center")) {
//            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//        } else {
//            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//        }
//        if (baseStyleModel.getPaddingTop() != null) {
//            cell.setPaddingTop(baseStyleModel.getPaddingTop());
//        }
//        if (baseStyleModel.getPaddingBottom() != null) {
//            cell.setPaddingBottom(baseStyleModel.getPaddingBottom());
//        }
//
//        pdfPCells[colIndex] = cell;
//    }
//
//    private static Font createFontByCss(CssStyleModel style) {
//        Font font = FontFactory.getFont("classpath:ttf/arialuni.ttf",
//                "Identity-H", BaseFont.EMBEDDED,
//                style.getFontSize(), style.getBold() ? Font.BOLD : Font.NORMAL);
//        int[] rgb = style.getFontColorRgb();
//        if (rgb != null && rgb.length == 3) {
//            font.setColor(new BaseColor(rgb[0], rgb[1], rgb[2]));
//        }
//        return font;
//    }
//
//    private static void updateCssModel(CssStyleModel res, Map<String, Map<String, String>> cssMap, String key) {
//        if (StringUtils.isBlank(key) || !cssMap.containsKey(key)) {
//            return;
//        }
//        Map<String, String> styleMap = cssMap.get(key);
//        res.setVerticalAlign(styleMap.get("vertical-align"));
//        res.setHorizontalAlign(styleMap.get("text-align"));
//        String fontSize = styleMap.getOrDefault("font-size", "").replaceAll("px", "");
//        if (StringUtils.isNotBlank(fontSize)) {
//            res.setFontSize(Integer.parseInt(fontSize));
//        }
//        String bold = styleMap.getOrDefault("font-weight", "");
//        res.setBold(StringUtils.equals(bold, "bold"));
//        String paddingTop = styleMap.getOrDefault("padding-top", "").replaceAll("px", "");
//        if (StringUtils.isNotBlank(paddingTop)) {
//            res.setPaddingTop(Integer.parseInt(paddingTop));
//        }
//        String paddingBottom = styleMap.getOrDefault("padding-bottom", "").replaceAll("px", "");
//        if (StringUtils.isNotBlank(paddingBottom)) {
//            res.setPaddingBottom(Integer.parseInt(paddingBottom));
//        }
//        String rgb = styleMap.getOrDefault("color", "").replaceAll("#", "");
//        rgb = rgb.replaceAll("rgb", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
//
//        if (StringUtils.isNotBlank(rgb)) {
//            String[] split = rgb.split(",");
//            int red = Integer.parseInt(split[0]);
//            int green = Integer.parseInt(split[1]);
//            int blue = Integer.parseInt(split[2]);
//            res.setFontColorRgb(new int[]{red, green, blue});
//        }
//    }
//
//    private static CssStyleModel generateCssModel(Map<String, Map<String, String>> cssMap, String key) {
//        CssStyleModel res = new CssStyleModel();
//        updateCssModel(res, cssMap, key);
//        return res;
//    }
//
//    private void generateHeaderCell(PdfPCell[] pdfPCells, PdfPRow row, Element td, int rowIndex, int rowspan, int colIndex, int colspan) throws Exception{
//        PdfPCell cell = new PdfPCell();
//        if (colspan > 1) {
//            cell.setColspan(colspan);
//        }
//        if (rowspan > 1) {
//            cell.setRowspan(rowspan);
//        }
//        PdfPTable innerTable = new PdfPTable(3);
//        innerTable.setWidthPercentage(100);
//        innerTable.setWidths(new float[]{1F,1F,1F});
//        innerTable.setPaddingTop(0);
//        innerTable.setSpacingAfter(0f);
//        innerTable.setSpacingAfter(0f);
//        Element imgEle = td.getElementsByTag("div").get(0).getElementsByTag("img").get(0);
//
//
//        BufferedImage targetImage = ImageIO.read(cetusStorageService.downloadToInputStream(imgEle.attr("src")));
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ImageIO.write(targetImage, "png", out);
//        Image image = Image.getInstance(out.toByteArray());
//        image.scaleAbsoluteHeight(52f);
//        image.scaleAbsoluteWidth(100f);
//
//
//
//        List<PdfPRow> innerRowList = innerTable.getRows();
//        PdfPRow innerRow = new PdfPRow(new PdfPCell[3]);
//        innerRowList.add(innerRow);
//        PdfPCell imageCell = new PdfPCell(image);
//        imageCell.setPaddingTop(8);
//        imageCell.setPaddingBottom(8);
//        imageCell.setPaddingLeft(10);
//
//        innerRow.getCells()[0] = imageCell;
//
//        Font font1 = FontFactory.getFont("classpath:ttf/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 20, Font.BOLD);
//        Font font2 = FontFactory.getFont("classpath:ttf/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 16, Font.BOLD);
//
//        Element span1 = td.getElementsByTag("span").get(0);
//        Element span3 = td.getElementsByTag("span").get(2);
//
//        Paragraph paragraph1 = new Paragraph(span1.text());
//        paragraph1.setFont(font1);
//        paragraph1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//        Paragraph paragraph2 = new Paragraph(span3.text());
//        paragraph2.setFont(font2);
//        PdfPCell innerCell2 = new PdfPCell();
//        innerCell2.addElement(paragraph1);
//        paragraph2.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//
//        innerCell2.addElement(paragraph2);
////        innerCell2.setLeading();
//        innerCell2.setPadding(0);
//        innerCell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//        innerCell2.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//        imageCell.setBorder(0);
//        innerCell2.setBorder(0);
//
//        innerRow.getCells()[1] = innerCell2;
//        PdfPCell innerCell3 = new PdfPCell(new Paragraph(""));
//        innerCell3.setPadding(0);
//        innerRow.getCells()[2] = innerCell3;
//        innerRow.getCells()[2].setBorder(0);
//
//        cell.addElement(innerTable);
//        pdfPCells[colIndex] = cell;
//    }
//
//    public static String getTemplate(String path, Object map) throws IOException, TemplateException {
//        Configuration cfg = new Configuration();
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        Template temp = new Template("inspection_export", new BufferedReader(new InputStreamReader(new FileInputStream(path))), cfg);
//        StringWriter stringWriter = new StringWriter();
//        temp.process(map, stringWriter);
//        return stringWriter.toString();
//    }
//}
