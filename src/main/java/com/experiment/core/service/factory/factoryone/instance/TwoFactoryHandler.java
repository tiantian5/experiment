package com.experiment.core.service.factory.factoryone.instance;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.dto.BaseDTO;
import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factoryone.FactoryOneEventConstructor;
import com.experiment.core.service.factory.factoryone.FactoryOneEventInterfaceHandler;

/**
 * @author tzw
 * @description 场景二
 * @create 2020-11-30 10:35 上午
 **/

@FactoryOneEventConstructor(EnumFactory.TWO_SCENE)
public class TwoFactoryHandler implements FactoryOneEventInterfaceHandler<BaseDTO> {

    @Override
    public void handle(BaseDTO eventDTO) {
        System.out.println("场景二" + JSONObject.toJSONString(eventDTO));
    }

}