package com.experiment.core.service.factory.factorytwo.instance;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factorytwo.DealFactoryTwoStrategy;
import org.springframework.stereotype.Service;

/**
 * @author tzw
 * @description 场景三
 * @create 2020-11-30 11:26 上午
 **/

@Service
public class ThreeFactoryStrategy implements DealFactoryTwoStrategy {

    @Override
    public void buildDealContext(Object... obj) {

    }

    @Override
    public void dealHandel(Object obj) {

    }

    @Override
    public Integer source() {
        return EnumFactory.THREE_SCENE.getType();
    }
}