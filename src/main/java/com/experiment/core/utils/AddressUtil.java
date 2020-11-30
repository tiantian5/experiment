
package com.experiment.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tzw
 * @description AddressUtil
 * @create 2020-02-20 12:18 下午
 **/
public class AddressUtil {

    private static final Pattern PATTERN_ONE = Pattern.compile("((?<provinceAndCity>[^市]+市|.*?自治州|.*?市|.*?区|.*县)(?<town>[^区]+区|.*?镇|.*?县|.*?乡))");
    private static final Pattern PATTERN_TWO = Pattern.compile("((?<province>[^省]+省|.+自治区|上海市|北京市|天津市|重庆市|上海|北京|天津|重庆)(?<city>.*))");
    private static final Pattern PATTERN_THREE = Pattern.compile("((?<province>[^省]+省|.*?自治州|.*?区|.*县)(?<town>[^区]+区|.*?市|.*?县|.*?自治县|.*?镇))");


    /**
     * 解析地址
     *
     * @param address 地址
     * @return 地址
     */
    public static String getAddressInfo(String address) {
        String province = null, city = null, provinceAndCity, town = null;
        Map<String, String> row = new LinkedHashMap<>();
        List<Map<String, String>> table = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        //普通地址
        Matcher m = PATTERN_ONE.matcher(address);
        boolean flag = Boolean.FALSE;
        while (m.find()) {
            flag = Boolean.TRUE;
            provinceAndCity = m.group("provinceAndCity");
            Matcher m2 = PATTERN_TWO.matcher(provinceAndCity);
            while (m2.find()) {
                province = m2.group("province");
                row.put("province", province == null ? "" : province.trim());
                city = m2.group("city");
                row.put("city", city == null ? "" : city.trim());
            }
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            table.add(row);
        }
        if (!m.find() && !flag) {
            Matcher m2 = PATTERN_THREE.matcher(address);
            while (m2.find()) {
                province = m2.group("province");
                row.put("province", province == null ? "" : province.trim());
                town = m2.group("town");
                row.put("town", town == null ? "" : town.trim());
                table.add(row);
            }
        }
        if (table.size() > 0) {
            if (StringUtils.isNotBlank(table.get(0).get("province"))) {
                province = table.get(0).get("province");
            }
            if (StringUtils.isNotBlank(table.get(0).get("city"))) {
                city = table.get(0).get("city");
            }
            if (StringUtils.isNotBlank(table.get(0).get("town"))) {
                town = table.get(0).get("town");
            }
        }
        return stringBuilder.append(StringUtils.isNotBlank(province) ? province : "")
                .append(StringUtils.isNotBlank(city) ? city : "")
                .append(StringUtils.isNotBlank(town) ? town : "").toString();
    }

    public static void main(String[] args) {
        System.out.println(getAddressInfo("湖北省黄冈市蕲春县蕲州镇矿建一关社区六小区62号苑江超市"));
        System.out.println(getAddressInfo("新疆维吾尔自治区喀什地区莎车县墩巴格乡人民政府"));
        System.out.println(getAddressInfo("河南省三门峡市会兴乡政府对面会兴粮行"));
        System.out.println(getAddressInfo("海南省陵水黎族自治县提蒙大道215号"));
        System.out.println(getAddressInfo("福建省龙岩市永定区坎市镇沿河中路149号吾雨白茶店"));
        System.out.println(getAddressInfo("广东省东莞市虎门镇虎门大道305号富民童装商务中心22楼检测中心"));
    }
}
