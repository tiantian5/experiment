package com.experiment.core.service.invoke;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/24/4:48 下午
 * @Description:
 */

@Slf4j
public class ConfigService {

    public static void test (OutConfigDTO outConfigDTO) {

        InConfigDTO inConfigDTO = new InConfigDTO();

        Field[] fields = OutConfigDTO.class.getDeclaredFields();
        Field.setAccessible(fields, true);

        Map<String, String> fieldMap = Maps.newHashMap();
        for (Field field : fields) {
            ConfigField configField = field.getAnnotation(ConfigField.class);
            if (configField == null) {
                continue;
            }
            String json = fieldMap.get(configField.value());
            JSONObject jsonObject = json != null ? JSON.parseObject(json) : new JSONObject();
            try {
                jsonObject.put(field.getName(), field.get(outConfigDTO));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fieldMap.put(configField.value(), JSON.toJSONString(jsonObject));
        }

        try {
            BeanUtils.populate(inConfigDTO, fieldMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("inConfigDTO is {}", JSONObject.toJSONString(inConfigDTO));

    }

    public static void main(String[] args) {

        OutConfigDTO outConfigDTO = new OutConfigDTO();
        outConfigDTO.setA("A");
        outConfigDTO.setB("B");
        outConfigDTO.setC("C");
        test(outConfigDTO);

    }

}
