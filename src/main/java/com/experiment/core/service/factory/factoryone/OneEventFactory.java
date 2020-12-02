package com.experiment.core.service.factory.factoryone;

import com.experiment.core.enums.EnumFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tzw
 * @description 具体获取类的工厂
 * @create 2020-11-30 10:29 上午
 **/

@Slf4j
@Component
public class OneEventFactory implements ApplicationContextAware {

    private static Map<EnumFactory, FactoryOneEventInterfaceHandler> wrapFilter = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(FactoryOneEventConstructor.class);
        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                FactoryOneEventConstructor factoryOneEventConstructor = entry.getValue().getClass().getAnnotation(FactoryOneEventConstructor.class);
                EnumFactory commonWxRobotEventTypeEnum = factoryOneEventConstructor.value();
                wrapFilter.put(commonWxRobotEventTypeEnum, (FactoryOneEventInterfaceHandler) entry.getValue());
            }
        }

    }

    /**
     * 获取某个子类
     *
     * @param eventType 类型
     * @return 获取某个子类
     */
    public FactoryOneEventInterfaceHandler getEventHandler(Integer eventType) {
        if (eventType == null) {
            return null;
        }
        EnumFactory factoryOne = EnumFactory.getByType(eventType);
        if (factoryOne == null) {
            return null;
        }
        return wrapFilter.get(factoryOne);
    }

}