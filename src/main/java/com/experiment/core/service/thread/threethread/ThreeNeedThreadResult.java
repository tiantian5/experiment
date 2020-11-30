package com.experiment.core.service.thread.threethread;

import com.experiment.core.service.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author tzw
 * @description 利用异步+CountDownLatch控制子线程执行。同样可以使用CyclicBarrier、Semaphore完成
 * <p>
 *     该方法适用与组装多个属性且有时间限制的场景 eg：商品详情页 将各个模块信息组装至上下文中返回
 *     注意点：使用到@Async注解，主方法不可与异步方法在同一个类中，否则不生效
 * </p>
 * @create 2020-11-30 3:20 下午
 **/

@Slf4j
@Service
public class ThreeNeedThreadResult {

    @Resource
    private AsyncDetailFacade asyncDetailFacade;

    public ThreadContext getInfo() {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        List<Future> asynTask = new ArrayList<>();

        ThreadContext threadContext = new ThreadContext();
        try {
            // 一、name
            asynTask.add(asyncDetailFacade.getName(threadContext, countDownLatch));

            // 二、age
            asynTask.add(asyncDetailFacade.getAge(threadContext, countDownLatch));

            boolean wait = countDownLatch.await(3, TimeUnit.SECONDS);
            if(!wait){
                throw new RuntimeException("存在子线程运行超时");
            }
        } catch (Exception e) {
            asynTask.forEach(item -> {
                if(item != null){
                    item.cancel(true);
                }
            });
            log.error("聚合异", e);
        }

        return threadContext;

    }

}