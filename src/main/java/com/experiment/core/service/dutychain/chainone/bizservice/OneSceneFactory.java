package com.experiment.core.service.dutychain.chainone.bizservice;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.dutychain.chainone.AbstractDetailStrategy;
import org.springframework.stereotype.Service;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:36 下午
 **/

@Service
public class OneSceneFactory extends AbstractDetailStrategy {

    @Override
    public Object getDetail(Object... obj) {
        return super.getDetail(obj);
    }

    @Override
    public Integer getType() {
        return EnumFactory.ONE_SCENE.getType();
    }
}