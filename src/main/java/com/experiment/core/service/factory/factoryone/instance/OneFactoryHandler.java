package com.experiment.core.service.factory.factoryone.instance;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.dto.BaseDTO;
import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factoryone.FactoryOneEventConstructor;
import com.experiment.core.service.factory.factoryone.FactoryOneEventInterfaceHandler;

/**
 * @author tzw
 * @description 场景一
 * @create 2020-11-30 10:35 上午
 **/

@FactoryOneEventConstructor(EnumFactory.ONE_SCENE)
public class OneFactoryHandler implements FactoryOneEventInterfaceHandler<BaseDTO> {

    @Override
    public void handle(BaseDTO eventDTO) {
        System.out.println("场景一" + JSONObject.toJSONString(eventDTO));
    }

}