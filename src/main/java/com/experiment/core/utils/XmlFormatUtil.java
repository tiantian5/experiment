package com.experiment.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * @author tzw
 * @description xml相互转化类
 * @create 2020-11-10 3:39 下午
 **/

@Slf4j
public class XmlFormatUtil {

    private static final String XML_HEAD_3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    /**
     * java bean对象转化为xml格式文本
     *
     * @param t java bean
     * @param <T> java bean
     * @return xml格式文本
     */
    public static <T> String javaBeanToXml(T t, Boolean isToOuter) {
        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(t, writer);
            String str = writer.toString();
            String replace = null;
            if (StringUtils.isNotBlank(str)) {
                replace = str.replace(XML_HEAD_3, "");
                if (isToOuter) {
                    replace = replace
                            .replace(toLowerCaseFirstOne(t.getClass().getSimpleName()), "response")
                            .trim();
                } else {
                    replace = replace
                            .replace(toLowerCaseFirstOne(t.getClass().getSimpleName()), "request")
                            .trim();
                }
            }
            return replace;
        } catch (Exception e) {
            log.error("XmlUtil javaBeanToXml t is {}", JSONObject.toJSONString(t), e);
        }
        return StringUtils.EMPTY;
    }

    public static <T> T xmlToJavaBean(String xmlStr, Class<T> tClass) {
        try {
            String replace = xmlStr;
            if (!xmlStr.startsWith("<?xml")) {
                replace = XML_HEAD_3.concat(xmlStr);
            }
            // 创建xstream对象
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("response", tClass);
//            xStream.alias("deliveryOrder", DeliveryOrder.class);
//            xStream.alias("orderLine", OrderLine.class);
            // 将字符串类型的xml转换为对象
            return (T) xStream.fromXML(replace);
        } catch (Exception e) {
            log.error("XmlUtil xmlToJavaBean xmlStr is {}", xmlStr, e);
        }

        return null;
    }

    /**
     * 根据xml生成对应的Java bean
     *
     * @param xml xml
     * @param cls 需转化的类
     * @param needDeleteNode 多余节点去除
     * @param <T> 类
     * @return 结果
     */
    public static <T> T xml2Obj(String xml, Class<?> cls, String ... needDeleteNode) {
        try {
            if (StringUtils.isBlank(xml)) {
                return null;
            }
            String replace = xml;
            for (String str : needDeleteNode) {
                replace = replace.replace("<" + str + ">", "")
                        .replace("</" + str + ">", "");
            }
            XStream xstream = createXstream();
            if (cls != null) {
                xstream.processAnnotations(cls);
            }
            return (T) xstream.fromXML(replace);
        } catch (Exception e) {
            log.error("XmlUtil xml2Obj xmlStr is {}", xml, e);
        }
        return null;
    }

    /**
     * 创建XStream
     */
    private static XStream createXstream() {
        XStream xstream = new XStream(new MyXppDriver(false));
        xstream.autodetectAnnotations(true);
        return xstream;
    }

    public static class MyXppDriver extends XppDriver {
        boolean useCDATA = false;

        MyXppDriver(boolean useCDATA) {
            super(new XmlFriendlyNameCoder("__", "_"));
            this.useCDATA = useCDATA;
        }
    }

    public static String toLowerCaseFirstOne(String s){
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

    public static void main(String[] args) {
        String res = "<response>\\n\" +\n" +
                "                \"    <success>true</success>\\n\" +\n" +
                "                \"    <errorcode>200</errorcode>\\n\" +\n" +
                "                \"    <errormsg>3</errormsg>\\n\" +\n" +
                "                \"    <deliveryOrder>\\n\" +\n" +
                "                \"        <deliveryOrderCode>12</deliveryOrderCode>\\n\" +\n" +
                "                \"        <warehouseCode>123</warehouseCode>\\n\" +\n" +
                "                \"        <sourcePlatformCode>123</sourcePlatformCode>\\n\" +\n" +
                "                \"        <status>123</status>\\n\" +\n" +
                "                \"        <statusName>123</statusName>\\n\" +\n" +
                "                \"        <statusTime>123</statusTime>\\n\" +\n" +
                "                \"        <customsClearance>123</customsClearance>\\n\" +\n" +
                "                \"        <customsClearanceNotes>123</customsClearanceNotes>\\n\" +\n" +
                "                \"        <customsClearanceTime>123</customsClearanceTime>\\n\" +\n" +
                "                \"        <logisticsCode>12.12</logisticsCode>\\n\" +\n" +
                "                \"        <logisticsName>123</logisticsName>\\n\" +\n" +
                "                \"        <expressCode>12.12</expressCode>\\n\" +\n" +
                "                \"    </deliveryOrder>\\n\" +\n" +
                "                \"    <orderLines>\\n\" +\n" +
                "                \"        <orderLine>\\n\" +\n" +
                "                \"            <itemCode>12.12</itemCode>\\n\" +\n" +
                "                \"            <itemId>123</itemId>\\n\" +\n" +
                "                \"            <itemName>12.12</itemName>\\n\" +\n" +
                "                \"            <extCode>123</extCode>\\n\" +\n" +
                "                \"            <planQty>123</planQty>\\n\" +\n" +
                "                \"            <actualQty>123</actualQty>\\n\" +\n" +
                "                \"            <expireDate>123</expireDate>\\n\" +\n" +
                "                \"        </orderLine>\\n\" +\n" +
                "                \"        <orderLine>\\n\" +\n" +
                "                \"            <itemCode>12.12</itemCode>\\n\" +\n" +
                "                \"            <itemId>123</itemId>\\n\" +\n" +
                "                \"            <itemName>12.12</itemName>\\n\" +\n" +
                "                \"            <extCode>123</extCode>\\n\" +\n" +
                "                \"            <planQty>123</planQty>\\n\" +\n" +
                "                \"            <actualQty>123</actualQty>\\n\" +\n" +
                "                \"            <expireDate>123</expireDate>\\n\" +\n" +
                "                \"        </orderLine>\\n\" +\n" +
                "                \"    </orderLines>\\n\" +\n" +
                "                \"</response>";

    }

}