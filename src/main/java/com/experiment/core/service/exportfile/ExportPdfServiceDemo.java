package com.experiment.core.service.exportfile;

import com.google.common.collect.Maps;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import com.tuya.csm.order.client.model.solder.SolderExportDTO;
import com.tuya.csm.order.service.vo.inspectiontask.InspectionItemExportDTO;
import com.tuya.csm.order.service.vo.inspectiontask.InspectionTaskExportDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
public class ExportPdfServiceDemo {
    public static void main(String[] args) throws Exception{
        InspectionTaskExportDTO exportDTO = getExportDTO();
//        SolderExportDTO exportDTO = getXigaoExportDTO();

        String htmlContent = getTemplate("/Users/song/Desktop/company_code/csm-order/csm-order-service/src/main/resources/inspection_ftl.html", exportDTO);
//        String htmlContent = getTemplate("/Users/song/Desktop/company_code/csm-order/csm-order-service/src/main/resources/xigao_ftl.html", exportDTO);
        System.out.println(htmlContent);
        writeFile("/Users/song/Desktop/company_code/csm-order/csm-order-service/src/main/resources/xigao_dest.html", htmlContent);
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
        generatePdf(cssMap, colgroup, trs);
    }

    private static SolderExportDTO getXigaoExportDTO() {
        SolderExportDTO res = new SolderExportDTO();
        res.setFactoryName("杭州涂鸦科技有限公司");
        res.setFactoryLogo("/Users/song/Desktop/tmp_pdf/logo.png");
        res.setReportName("锡膏/红胶使用报告");
        res.setSolderTypeName("锡膏编码");
        res.setSolderPasteCode("5120210001142");
        res.setModel("2P排线(2651-28-L=70)");
        res.setSpecification("2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子");
        res.setBatchNo("123");
        res.setSupplierName("中山市振宇电子有限公司");
        res.setProductionDate("2022/7/20");
        res.setShelfLife("90");
        res.setStorageTime("2022/7/20 10:00");
        res.setExporter("张三");
        res.setExportTime("2022/7/20 10:00:00");

        List<SolderExportDTO.RecordInfoDTO> recordInfoList = Lists.newArrayList();

        SolderExportDTO.RecordInfoDTO item = new SolderExportDTO.RecordInfoDTO();
        item.setStep(1);
        item.setStepType(1);
        item.setUseDesc("测试下");
        item.setUseTimeDesc("2021-12-12");
        item.setUseTime(System.currentTimeMillis());
        item.setTimeDesc("12");
        recordInfoList.add(item);

        res.setRecordInfoDTO(recordInfoList);
        return res;
    }

    private static InspectionTaskExportDTO getExportDTO() {
        InspectionTaskExportDTO res = new InspectionTaskExportDTO();
        res.setFactoryName("杭州涂鸦科技有限公司");
        res.setFactoryLogo("/Users/song/Desktop/tmp_pdf/logo.png");
        res.setReportName("来料质检报告");
        res.setMaterialCode("1.01.01.1");
        res.setMaterialName("测试物料");
        res.setMaterialModel("测试型号");
        res.setMaterialDesc("测试描述");
        res.setSupplierCode("supplier001");
        res.setSupplierName("供应商001");
        res.setOrderNo("s-0001-02");
        res.setInspectionTaskNumber(120);
        res.setCreateDate("2022-12-12 12:12:12");
        res.setExecuteDate("2022-12-11 12:12:12");
        res.setPlanName("标准测试");
        res.setInspectionResult("通过");
        res.setInspectionExecute("允收");
        res.setExecutor("展昭");
        res.setExportTime("2022-12-21 00:12:16");
        res.setExporter("展昭");
        List<InspectionItemExportDTO> itemList = Lists.newArrayList();
        InspectionItemExportDTO item1 = new InspectionItemExportDTO();
        InspectionItemExportDTO item2 = new InspectionItemExportDTO();
        itemList.add(item1);
        itemList.add(item2);

        item1.setSeq("1");
        item1.setInspectionItemName("包装");
        item1.setInspectionItemDescribe("1、产品包材完好，包装应防潮，包装袋上应标明物料编码、数量、名称、规格型号、生产日期、状态及生产厂家");
        item1.setToolName("日光灯/静电测试仪(1)");
        item1.setItemResult("通过");
        item1.setAql("0.650");
        item1.setAc("2");
        item1.setRe("3");
        item1.setSampleLevel("II");
        item1.setSampleNumber("120");
        item1.setSuccessNum("21");
        item1.setFailedNum("22");
        item1.setGuidelineList(Lists.newArrayList());

        item2.setSeq("1");
        item2.setInspectionItemName("包装");
        item2.setInspectionItemDescribe("1、产品包材完好，包装应防潮，包装袋上应标明物料编码、数量、名称、规格型号、生产日期、状态及生产厂家");
        item2.setToolName("日光灯/静电测试仪(1)");
        item2.setItemResult("不通过");
        item2.setAql("0.650");
        item2.setAc("2");
        item2.setRe("3");
        item2.setSampleLevel("II");
        item2.setSampleNumber("120");
        item2.setSuccessNum("21");
        item2.setFailedNum("22");
        item2.setFailReason("1.这个不好；\r\n,2.那个不好");


        List<InspectionItemExportDTO.InspectionGuideline> guidelineList = Lists.newArrayList();

        InspectionItemExportDTO.InspectionGuideline guideline1 = new InspectionItemExportDTO.InspectionGuideline();
        guideline1.setGuideReference("长度 70～90 mm");
        guideline1.setGuideRes("72，75，79 mm");

        InspectionItemExportDTO.InspectionGuideline guideline2 = new InspectionItemExportDTO.InspectionGuideline();
        guideline2.setGuideReference("宽度 50～60 mm");
        guideline2.setGuideRes("12， 56 mm");

        guidelineList.add(guideline1);
        guidelineList.add(guideline2);
        item2.setGuidelineList(guidelineList);

        res.setItemList(itemList);
        return res;
    }

    public static void writeFile(String path, String content) throws Exception{
        File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(content); // \r\n即为换行
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
    }

    private static void generatePdf(Map<String, Map<String, String>> cssMap, Element colgroup, Elements trs) throws Exception{
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
        Float marginLeft = Float.parseFloat(body.get("margin-left").replaceAll("px", ""));
        Float marginRight = Float.parseFloat(body.get("margin-right").replaceAll("px", ""));
        Float marginTop = Float.parseFloat(body.get("margin-top").replaceAll("px", ""));
        Float marginBottom = Float.parseFloat(body.get("margin-bottom").replaceAll("px", ""));

        //创建文件
        Document document = new Document(PageSize.A4.rotate());

        //建立一个书写器
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream out = new FileOutputStream(new File("/Users/song/Desktop/tmp_pdf/101.pdf"));

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
        Font font = FontFactory.getFont("/Users/song/Desktop/company_code/csm-order/csm-order-common/src/main/resources/img/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, Integer.parseInt(fontSize), StringUtils.equals(bold, "bold") ? Font.BOLD : Font.NORMAL);
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

    private static void generateHeaderCell(PdfPCell[] pdfPCells, PdfPRow row, Element td, int rowIndex, int rowspan, int colIndex, int colspan) throws Exception{
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


        BufferedImage targetImage = ImageIO.read(new FileInputStream(imgEle.attr("src")));
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

        Font font1 = FontFactory.getFont("/Users/song/Desktop/company_code/csm-order/csm-order-common/src/main/resources/img/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 20, Font.BOLD);
        Font font2 = FontFactory.getFont("/Users/song/Desktop/company_code/csm-order/csm-order-common/src/main/resources/img/arialuni.ttf", "Identity-H", BaseFont.EMBEDDED, 16, Font.BOLD);

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
