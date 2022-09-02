//package com.experiment.core.service.exportfile;
//
//import com.google.common.collect.Maps;
//import com.steadystate.css.parser.CSSOMParser;
//import com.steadystate.css.parser.SACParserCSS3;
//import com.tuya.csm.order.common.util.BeanCopyUtil;
//import com.tuya.csm.order.service.vo.inspectiontask.InspectionItemExportDTO;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateExceptionHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.compress.utils.Lists;
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.util.RegionUtil;
//import org.apache.poi.util.IOUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//import org.w3c.css.sac.InputSource;
//import org.w3c.dom.css.CSSRule;
//import org.w3c.dom.css.CSSRuleList;
//import org.w3c.dom.css.CSSStyleSheet;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author wusongsong
// * @date 2022/8/3
// */
//@Slf4j
//@Service
//public class ExportExcelServiceDemo {
//    private static final String BASE_PATH = "/Users/song/Desktop/tmp_pdf/";
//
//    public static void main(String[] args) throws Exception{
////        SolderExportDTO exportDTO = getXigaoExportDTO();
//        InspectionTaskExportDTO exportDTO = getExportDTO();
//
//        String htmlContent = getTemplate("/Users/song/Desktop/company_code/csm-order/csm-order-service/src/main/resources/inspection_ftl.html", exportDTO);
////        String htmlContent = getTemplate("/Users/song/Desktop/company_code/csm-order/csm-order-service/src/main/resources/xigao_ftl.html", exportDTO);
//        System.out.println(htmlContent);
//        Document document = Jsoup.parse(htmlContent);
//        Element style = document.head().select("style").first();
//
//        InputSource source = new InputSource(
//                new StringReader(
//                        style.html()));
//        CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
//        CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
//        CSSRuleList rules = sheet.getCssRules();
//        Map<String, Map<String, String>> cssMap = Maps.newHashMap();
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
//        generateExcel(cssMap, colgroup, trs);
//    }
//
//    private static SolderExportDTO getXigaoExportDTO() {
//        SolderExportDTO res = new SolderExportDTO();
//        res.setFactoryName("杭州涂鸦科技有限公司");
//        res.setFactoryLogo("/Users/song/Desktop/tmp_pdf/logo.png");
//        res.setReportName("锡膏/红胶使用报告");
//        res.setSolderTypeName("锡膏编码");
//        res.setSolderPasteCode("5120210001142");
//        res.setModel("2P排线(2651-28-L=70)");
//        res.setSpecification("2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子2P,UL2651,28AWG,L=70mm灰色,一端PHB-2P端子,另一端SCN-2P端子");
//        res.setBatchNo("123");
//        res.setSupplierName("中山市振宇电子有限公司");
//        res.setProductionDate("2022/7/20");
//        res.setShelfLife("90");
//        res.setStorageTime("2022/7/20 10:00");
//        res.setExporter("张三");
//        res.setExportTime("2022/7/20 10:00:00");
//
//        List<SolderExportDTO.RecordInfoDTO> recordInfoList = Lists.newArrayList();
//
//        SolderExportDTO.RecordInfoDTO item = new SolderExportDTO.RecordInfoDTO();
//        item.setStep(1);
//        item.setStepType(1);
//        item.setUseDesc("测试下");
//        item.setUseTimeDesc("2021-12-12");
//        item.setUseTime(System.currentTimeMillis());
//        item.setTimeDesc("12");
//        recordInfoList.add(item);
//
//        res.setRecordInfoDTO(recordInfoList);
//        return res;
//    }
//
//    private static InspectionTaskExportDTO getExportDTO() {
//        InspectionTaskExportDTO res = new InspectionTaskExportDTO();
//        res.setFactoryName("杭州涂鸦科技有限公司");
//        res.setFactoryLogo("/Users/song/Desktop/tmp_pdf/logo.png");
//        res.setReportName("来料质检报告");
//        res.setMaterialCode("1.01.01.1");
//        res.setMaterialName("测试物料");
//        res.setMaterialModel("测试型号");
//        res.setMaterialDesc("测试描述");
//        res.setSupplierCode("supplier001");
//        res.setSupplierName("供应商001");
//        res.setOrderNo("s-0001-02");
//        res.setInspectionTaskNumber(120);
//        res.setCreateDate("2022-12-12 12:12:12");
//        res.setExecuteDate("2022-12-11 12:12:12");
//        res.setPlanName("标准测试");
//        res.setInspectionResult("通过");
//        res.setInspectionExecute("允收");
//        res.setExecutor("展昭");
//        res.setExportTime("2022-12-21 00:12:16");
//        res.setExporter("展昭");
//        List<InspectionItemExportDTO> itemList = Lists.newArrayList();
//        InspectionItemExportDTO item1 = new InspectionItemExportDTO();
//        InspectionItemExportDTO item2 = new InspectionItemExportDTO();
//        itemList.add(item1);
//        itemList.add(item2);
//
//        item1.setSeq("1");
//        item1.setInspectionItemName("包装");
//        item1.setInspectionItemDescribe("1、产品包材完好，包装应防潮，包装袋上应标明物料编码、数量、名称、规格型号、生产日期、状态及生产厂家");
//        item1.setToolName("日光灯/静电测试仪(1)");
//        item1.setItemResult("通过");
//        item1.setAql("0.650");
//        item1.setAc("2");
//        item1.setRe("3");
//        item1.setSampleLevel("II");
//        item1.setSampleNumber("120");
//        item1.setSuccessNum("21");
//        item1.setFailedNum("22");
//        item1.setGuidelineList(Lists.newArrayList());
//
//        item2.setSeq("1");
//        item2.setInspectionItemName("包装");
//        item2.setInspectionItemDescribe("1、产品包材完好，包装应防潮，包装袋上应标明物料编码、数量、名称、规格型号、生产日期、状态及生产厂家");
//        item2.setToolName("日光灯/静电测试仪(1)");
//        item2.setItemResult("不通过");
//        item2.setAql("0.650");
//        item2.setAc("2");
//        item2.setRe("3");
//        item2.setSampleLevel("II");
//        item2.setSampleNumber("120");
//        item2.setSuccessNum("21");
//        item2.setFailedNum("22");
//        item2.setFailReason("1.这个不好;|2.那个不好");
//
//
//        List<InspectionItemExportDTO.InspectionGuideline> guidelineList = Lists.newArrayList();
//
//        InspectionItemExportDTO.InspectionGuideline guideline1 = new InspectionItemExportDTO.InspectionGuideline();
//        guideline1.setGuideReference("长度 70～90 mm");
//        guideline1.setGuideRes("72，75，79 mm");
//
//        InspectionItemExportDTO.InspectionGuideline guideline2 = new InspectionItemExportDTO.InspectionGuideline();
//        guideline2.setGuideReference("宽度 50～60 mm");
//        guideline2.setGuideRes("12， 56 mm");
//
//        guidelineList.add(guideline1);
//        guidelineList.add(guideline2);
//        item2.setGuidelineList(guidelineList);
//
//        res.setItemList(itemList);
//        return res;
//    }
//
//
//    private static void generateExcel(Map<String, Map<String, String>> cssMap, Element colgroup, Elements trs) throws Exception{
//        Elements cols = colgroup.getElementsByTag("col");
//        int size = cols.size();
//        float[] widthArray = new float[size];
//        int i = 0;
//        float total = 0;
//        for (Element col : cols) {
//            String className = col.attr("class");
//            Map<String, String> attrMap = cssMap.get("." + className);
//            String width = attrMap.get("width");
//            float px = Float.parseFloat(width.replaceAll("px", ""));
//            widthArray[i++] = px;
//            total += px;
//        }
//
//        Map<String, String> body = cssMap.get("body");
//        HSSFWorkbook wb = new HSSFWorkbook(); //or new HSSFWorkbook();
//        //create sheet
//        Sheet sheet = wb.createSheet();
//        int totalWidth = 30000;
//        for (int j = 0; j < widthArray.length; j++) {
////            int w = (int)widthArray[j];
////            sheet.setColumnWidth(j, w / 5 * 256);
//            float ratio = widthArray[j] / total;
//            sheet.setColumnWidth(j, (int)(totalWidth * ratio));
//        }
////        sheet.setColumnWidth(0, 13 * 256);
////        sheet.setColumnWidth(1, 18 * 256);
////        sheet.setColumnWidth(2, 20 * 256);
////        sheet.setColumnWidth(3, 23 * 256);
////        sheet.setColumnWidth(4, 14 * 256);
////        sheet.setColumnWidth(5, 17 * 256);
////        sheet.setColumnWidth(6, 14 * 256);
//
//        sheet.setDefaultRowHeight((short)(25 * 20));
//
//
//        int rowIndex = 0;
//        Map<Integer, Integer> col2RowMax = Maps.newHashMap();
//        Map<String, Integer> colorIndexMap = Maps.newHashMap();
//        AtomicInteger colorIndex = new AtomicInteger(0x8);
//
//        for (Element tr : trs) {
//            Elements tds = tr.getElementsByTag("td");
//            Row rowA = sheet.createRow(rowIndex);
//            rowA.setHeight((short)(25 * 20));
//            int colIndex = 0;
//            for (Element td : tds) {
//                String colspanStr = td.attr("colspan");
//                String rowspanStr = td.attr("rowspan");
//                int colspan = 1;
//                int rowspan = 1;
//                if (org.apache.commons.lang3.StringUtils.isNotBlank(colspanStr)) {
//                    colspan = Integer.parseInt(colspanStr);
//                }
//                if (org.apache.commons.lang3.StringUtils.isNotBlank(rowspanStr)) {
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
//                    Cell cell = rowA.createCell(colIndex);
//                    HSSFCellStyle cellStyle = wb.createCellStyle();
//                    cellStyle.setBorderLeft(BorderStyle.THIN);
//                    cellStyle.setBorderRight(BorderStyle.THIN);
//                    cellStyle.setBorderTop(BorderStyle.THIN);
//                    cellStyle.setBorderBottom(BorderStyle.THIN);
//                    cell.setCellStyle(cellStyle);
//                    colIndex++;
//                }
//                if (colIndex >= widthArray.length) {
//                    break;
//                }
//                if (rowIndex == 0) {
//                    createHeadCell(wb, sheet,td, rowIndex, colIndex, rowspan, colspan);
//                } else {
//                    createCell(rowA, cssMap, wb, sheet,td, rowIndex, colIndex, rowspan, colspan, colorIndex, colorIndexMap);
//                }
//                if (rowspan > 1) {
//                    col2RowMax.put(colIndex, rowIndex + rowspan - 1);
//                }
//                colIndex += colspan;
//            }
//            rowIndex++;
//        }
//        String file = BASE_PATH + "i.xls";
//        OutputStream fileOut = new FileOutputStream(file);
//        wb.write(fileOut);
//    }
//
//    private static void createCell(Row row, Map<String, Map<String, String>> cssMap, HSSFWorkbook wb, Sheet sheet, Element td,int rowStart, int colStart, int rowspan, int colspan, AtomicInteger colorIndex, Map<String, Integer> colorMap) {
//        CellRangeAddress region = null;
//        if (colspan > 1 || rowspan > 1) {
//            region = new CellRangeAddress(rowStart, rowStart+rowspan-1, colStart, colStart+colspan-1);
//            sheet.addMergedRegion(region);
//        }
//        Cell cell = row.createCell(colStart);
//        String styleKey = td.attr("class");
//        CssStyleModel baseStyleModel = generateCssModel(cssMap, "." + styleKey);
//
//        Elements spans = td.getElementsByTag("span");
//        CellStyle cellStyle = wb.createCellStyle();
//        if (CollectionUtils.isNotEmpty(spans)) {
//            int[][] indexArr = new int[spans.size()][];
//            HSSFFont[] fontArray = new HSSFFont[spans.size()];
//
//            StringBuilder builder = new StringBuilder();
//            int index = 0;
//            for (Element span : spans) {
//                CssStyleModel itemStyle = BeanCopyUtil.copyWithId(baseStyleModel, CssStyleModel.class);
//                String spanClass = span.attr("class");
//                updateCssModel(itemStyle, cssMap, "." + spanClass);
//                HSSFFont itemFont = createFontByCss(wb, colorMap, colorIndex, itemStyle);
//                fontArray[index] = itemFont;
//                int start = builder.length();
//                Elements brs = span.getElementsByTag("br");
//                if (CollectionUtils.isNotEmpty(brs)) {
//                    builder.append("\r\n");
//                } else {
//                    builder.append(span.text().replaceAll("\\|", "\r\n"));
//                }
//                int end = builder.length();
//                indexArr[index] = new int[]{start, end};
//                index++;
//            }
//            HSSFRichTextString richStr = new HSSFRichTextString(builder.toString());
//            for (int i = 0; i < spans.size(); i++) {
//                HSSFFont hssfFont = fontArray[i];
//                int[] indexPair = indexArr[i];
//                richStr.applyFont(indexPair[0], indexPair[1], hssfFont);
//            }
//            cell.setCellValue(richStr);
//        } else {
//            cellStyle.setFont(createFontByCss(wb, colorMap, colorIndex, baseStyleModel));
//            cell.setCellValue(td.text());
//        }
//
//
//        cellStyle.setWrapText(true);
//        if (StringUtils.equals(baseStyleModel.getVerticalAlign(), "middle")) {
//            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        }
//        if (StringUtils.equals(baseStyleModel.getHorizontalAlign(), "center")) {
//            cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        } else {
//            cellStyle.setAlignment(HorizontalAlignment.LEFT);
//        }
//
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//
//        if (region != null) {
//            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
//        }
//        cell.setCellStyle(cellStyle);
//    }
//
//    private static HSSFFont createFontByCss(HSSFWorkbook wb, Map<String, Integer> colorMap, AtomicInteger colorIndex, CssStyleModel style) {
//        HSSFFont font = wb.createFont();
//        font.setFontName("等线");
//        font.setFontHeightInPoints(style.getFontSize().shortValue());
//        int[] rgb = style.getFontColorRgb();
//        if (rgb != null && rgb.length == 3) {
//            byte[] rgbArray = new byte[] {(byte) rgb[0], (byte) rgb[1], (byte) rgb[2]};
////            if (font instanceof XSSFFont) {
////                XSSFFont xssfFont = (XSSFFont)font;
////                xssfFont.setColor(new XSSFColor(rgb, null));
////            } else if (font instanceof HSSFFont) {
////                font.setColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
////                HSSFWorkbook hssfworkbook = (HSSFWorkbook)workbook;
////                HSSFPalette palette = hssfworkbook.getCustomPalette();
////                palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LIME.getIndex(), rgb[0], rgb[1], rgb[2]);
////            }
//            String colorKey = rgb[0] + "_" + rgb[1] + "_" + rgb[2];
//            Integer existColorIndex = colorMap.get(colorKey);
//            if (existColorIndex == null) {
//                colorIndex.addAndGet(1);
//                font.setColor((short) colorIndex.get());
//                HSSFPalette palette = wb.getCustomPalette();
//                palette.setColorAtIndex((short) colorIndex.get(), rgbArray[0], rgbArray[1], rgbArray[2]);
//                colorMap.put(colorKey, colorIndex.get());
//            } else {
//                font.setColor(existColorIndex.shortValue());
//            }
//        }
//        font.setBold(style.getBold());
//        return font;
//    }
//
//    private static void updateCssModel(CssStyleModel res, Map<String, Map<String, String>> cssMap, String key) {
//        if (org.apache.commons.lang3.StringUtils.isBlank(key) || !cssMap.containsKey(key)) {
//            return;
//        }
//        Map<String, String> styleMap = cssMap.get(key);
//        res.setVerticalAlign(styleMap.get("vertical-align"));
//        res.setHorizontalAlign(styleMap.get("text-align"));
//        String fontSize = styleMap.getOrDefault("font-size", "").replaceAll("px", "");
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(fontSize)) {
//            res.setFontSize(Integer.parseInt(fontSize));
//        }
//        String bold = styleMap.getOrDefault("font-weight", "");
//        res.setBold(org.apache.commons.lang3.StringUtils.equals(bold, "bold"));
//        String paddingTop = styleMap.getOrDefault("padding-top", "").replaceAll("px", "");
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(paddingTop)) {
//            res.setPaddingTop(Integer.parseInt(paddingTop));
//        }
//        String paddingBottom = styleMap.getOrDefault("padding-bottom", "").replaceAll("px", "");
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(paddingBottom)) {
//            res.setPaddingBottom(Integer.parseInt(paddingBottom));
//        }
//        String rgb = styleMap.getOrDefault("color", "").replaceAll("#", "");
//        rgb = rgb.replaceAll("rgb", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
//
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(rgb)) {
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
//    private static void createHeadCell(HSSFWorkbook wb, Sheet sheet, Element td,int rowStart, int colStart, int rowspan, int colspan) throws Exception{
//        CellRangeAddress region = new CellRangeAddress(rowStart, rowStart+rowspan-1, colStart, colStart+colspan-1);
//        sheet.addMergedRegion(region);
//
//        Row row = sheet.createRow(rowStart);
//        row.setHeight((short)(64 * 20));
//        Cell cell = row.createCell(colStart);
//
//        HSSFFont font1 = wb.createFont();
//        font1.setFontName("等线");
//        font1.setFontHeightInPoints((short)20);
//        font1.setBold(true);
//
//        HSSFFont font2 = wb.createFont();
//        font2.setFontName("等线");
//        font2.setFontHeightInPoints((short)16);
//        font2.setBold(true);
//        Element span1 = td.getElementsByTag("span").get(0);
//        Element span3 = td.getElementsByTag("span").get(2);
//
//        HSSFRichTextString ts = new HSSFRichTextString(span1.text() + "\r\n" + span3.text());
//        ts.applyFont(0, span1.text().length(), font1);
//        ts.applyFont(span1.text().length()+1, ts.length(), font2);
//
//
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle.setWrapText(true);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//
//        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
//        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
//
//        cell.setCellStyle(cellStyle);
//        cell.setCellValue(ts);
//
//        //add picture data to this workbook.
//        Element imgEle = td.getElementsByTag("div").get(0).getElementsByTag("img").get(0);
//
//        InputStream is = new FileInputStream(imgEle.attr("src"));
//        byte[] bytes = IOUtils.toByteArray(is);
//        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
//        InputStream isA = new ByteArrayInputStream(bytes);
//        BufferedImage sourceImg = ImageIO.read(isA);
//
//        CreationHelper helper = wb.getCreationHelper();
//        // Create the drawing patriarch.  This is the top level container for all shapes.
//        Drawing drawing = sheet.createDrawingPatriarch();
//        //add a picture shape
//        ClientAnchor anchor = helper.createClientAnchor();
//        //set top-left corner of the picture,
//        //subsequent call of Picture#resize() will operate relative to it
//        anchor.setCol1(colStart);
//        anchor.setRow1(rowStart);
//        anchor.setDx1(200);
//        anchor.setDy1(20);
//        Picture pict = drawing.createPicture(anchor, pictureIdx);
//        //auto-size picture relative to its top-left corner
//        double imageWidth = sourceImg.getWidth();
//        double imageHeight = sourceImg.getHeight();
//        // 获取单元格宽度和高度，单位都是像素
//        double cellWidth = sheet.getColumnWidthInPixels(cell.getColumnIndex());
//        double cellHeight = cell.getRow().getHeightInPoints() / 72 * 96;// getHeightInPoints()方法获取的是点（磅），就是excel设置的行高，1英寸有72磅，一般显示屏一英寸是96个像素
//        // 插入图片，如果原图宽度大于最终要求的图片宽度，就按比例缩小，否则展示原图
//        if (imageWidth > cellHeight) {
//            double scaleX = cellHeight / cellWidth;// 最终图片大小与单元格宽度的比例
//            // 最终图片大小与单元格高度的比例
//            // 说一下这个比例的计算方式吧：( imageHeight / imageWidth ) 是原图高于宽的比值，则 ( width * ( imageHeight / imageWidth ) ) 就是最终图片高的比值，
//            // 那 ( width * ( imageHeight / imageWidth ) ) / cellHeight 就是所需比例了
//            double scaleY = ( cellHeight * ( imageHeight / imageWidth ) ) / cellHeight;
//            pict.resize(scaleX, scaleY);
//        } else {
//            pict.resize();
//        }
//        is.close();
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
