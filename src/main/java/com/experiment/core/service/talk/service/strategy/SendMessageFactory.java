package com.experiment.core.service.talk.service.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:00 下午
 * @Description:
 */

@Configuration
public class SendMessageFactory implements InitializingBean {

    @Resource
    private List<SendMessageStrategy> sendMessageStrategies;

    Map<Integer, SendMessageStrategy> sendMessageStrategyConcurrentHashMap = new ConcurrentHashMap<>(16);

    public SendMessageStrategy getDealFactoryTwoStrategy(Integer source) { return sendMessageStrategyConcurrentHashMap.get(source); }

    @Override
    public void afterPropertiesSet() {

        sendMessageStrategyConcurrentHashMap = sendMessageStrategies.parallelStream().collect(
                Collectors.toMap(SendMessageStrategy::source, Function.identity()));

    }

}
