package com.experiment.core.service.thread.twothread;

import com.experiment.core.service.thread.ThreadContext;
import com.experiment.core.service.thread.twothread.service.AgeService;
import com.experiment.core.service.thread.twothread.service.NameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author tzw
 * @description 利用CompletableFuture做到将子任务收集等待
 * @create 2020-11-30 3:08 下午
 **/

@Slf4j
@Service
public class TwoNeedThreadResult {

    @Autowired
    private Executor detailThreadPool;

    @Resource
    private NameService nameService;

    @Resource
    private AgeService ageService;

    public ThreadContext getContextByThread(Object ... obj) {

        ThreadContext threadContext = new ThreadContext();

        CompletableFuture nameFuture = CompletableFuture.runAsync(() -> {
            try{
                threadContext.setName(nameService.getName());

            } catch (Exception e) {
                log.error("名称获取异常：obj：{}", obj, e);
            }

        }, detailThreadPool);

        CompletableFuture ageFuture = CompletableFuture.runAsync(() -> {
            try{
                threadContext.setAge(ageService.getAge());
            } catch (Exception e) {
                log.error("年龄获取异常：obj：{}", obj, e);
            }

        }, detailThreadPool);

        try {
            CompletableFuture.allOf(nameFuture, ageFuture).get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("[主接口发生异常]，obj：{}", obj, e);
        }

        return threadContext;

    }


}