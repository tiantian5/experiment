package com.experiment.core.service.factory.factorythree;

import com.experiment.core.dto.BaseDTO;
import com.experiment.core.enums.EnumFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 11:29 上午
 **/

@Service
public class ThreeFactoryServiceDeal implements InitializingBean {

    @Resource
    private List<ThreeFactoryDrainage> drainageList;

    private HashMap<Integer, List<ThreeFactoryDrainage>> workOrderTypeMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        drainageList.forEach(item -> {
            List<Integer> adaptive = item.selfAdaption();
            adaptive.forEach(t -> {
                List<ThreeFactoryDrainage> threeFactoryDrainageList = Optional.ofNullable(workOrderTypeMap.get(t)).orElse(new ArrayList<>());
                threeFactoryDrainageList.add(item);
                workOrderTypeMap.put(t, threeFactoryDrainageList);
            });
        });
    }

    /**
     * 业务处理
     */
    public void deal() {

        List<ThreeFactoryDrainage> threeFactoryDrainageList = workOrderTypeMap.get(EnumFactory.ONE_SCENE.getType());

        for (ThreeFactoryDrainage threeFactoryDrainage : threeFactoryDrainageList) {

            threeFactoryDrainage.dealWorkOrder(new BaseDTO());

        }
    }

}