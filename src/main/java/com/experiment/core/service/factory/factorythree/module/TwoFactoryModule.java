package com.experiment.core.service.factory.factorythree.module;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factorythree.ThreeFactoryDrainage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 11:34 上午
 **/

@Service("twoFactoryModule")
public class TwoFactoryModule implements ThreeFactoryDrainage {

    @Override
    public void dealWorkOrder(Object obj) {

    }

    @Override
    public List<Integer> selfAdaption() {
        return new ArrayList<Integer>(){{
            add(EnumFactory.TWO_SCENE.getType());
        }};

    }
}