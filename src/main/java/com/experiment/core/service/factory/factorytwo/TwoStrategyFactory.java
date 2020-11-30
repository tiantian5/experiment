package com.experiment.core.service.factory.factorytwo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tzw
 * @description 工厂策略类
 * @create 2020-11-30 11:10 上午
 **/

@Configuration
public class TwoStrategyFactory implements InitializingBean {

    @Resource
    private List<DealFactoryTwoStrategy> dealFactoryTwoStrategieList;
    Map<Integer, DealFactoryTwoStrategy> dealFactoryTwoStrategyConcurrentHashMap = new ConcurrentHashMap<>(16);

    public DealFactoryTwoStrategy getDealFactoryTwoStrategy(Integer source) { return dealFactoryTwoStrategyConcurrentHashMap.get(source); }

    @Override
    public void afterPropertiesSet() throws Exception {

        dealFactoryTwoStrategyConcurrentHashMap = dealFactoryTwoStrategieList.parallelStream().collect(Collectors.toMap(DealFactoryTwoStrategy::source, Function.identity()));

    }

}