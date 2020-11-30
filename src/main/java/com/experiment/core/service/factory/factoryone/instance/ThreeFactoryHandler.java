package com.experiment.core.service.factory.factoryone.instance;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.dto.BaseDTO;
import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factoryone.FactoryOneEventConstructor;
import com.experiment.core.service.factory.factoryone.FactoryOneEventInterfaceHandler;

/**
 * @author tzw
 * @description 场景三
 * @create 2020-11-30 10:35 上午
 **/

@FactoryOneEventConstructor(EnumFactory.THREE_SCENE)
public class ThreeFactoryHandler implements FactoryOneEventInterfaceHandler<BaseDTO> {

    @Override
    public void handle(BaseDTO eventDTO) {
        System.out.println("场景三" + JSONObject.toJSONString(eventDTO));
    }

}