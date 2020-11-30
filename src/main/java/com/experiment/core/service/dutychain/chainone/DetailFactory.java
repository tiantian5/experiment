package com.experiment.core.service.dutychain.chainone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tzw
 * @description 工厂
 * @create 2020-11-30 2:14 下午
 **/

@Service
public class DetailFactory {

    @Autowired
    private List<DetailStrategy> pitemDetailStrategies;

    /**
     * 根据类型获取策略
     *
     * @param type 类型
     * @return 策略
     */
    public DetailStrategy getStrategy(Integer type){

        for (DetailStrategy detailStrategy : pitemDetailStrategies) {
            if(detailStrategy.getType().equals(type)){
                return detailStrategy;
            }
        }

        return null;
    }

}