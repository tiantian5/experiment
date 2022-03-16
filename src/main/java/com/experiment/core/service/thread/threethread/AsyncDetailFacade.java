package com.experiment.core.service.thread.threethread;

import com.experiment.core.service.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author tzw
 * @description 异步执行方法
 * @create 2020-11-30 3:27 下午
 **/

@Slf4j
@Service
public class AsyncDetailFacade {

    @Async(value = "asyncServiceExecutor")
    public Future<?> getName (ThreadContext threadContext, CountDownLatch countDownLatch) {
        try {
            threadContext.setName("name");
//            Thread.sleep(10000);
            for (int i = 0; i < 10; i++) {
                threadContext.setName("sasa");
                for (int j = 0; j < 1000000000; j++) {
                    threadContext.setName("sasa");
                }
            }
            log.info("执行完了");
        } catch (Exception e) {
            log.error("AAA异常", e);
        } finally {
            if(countDownLatch != null){
                countDownLatch.countDown();
            }
        }
        return new AsyncResult<>("");
    }

    @Async(value = "asyncServiceExecutor")
    public Future<?> getAge (ThreadContext threadContext, CountDownLatch countDownLatch) {
        try {
            threadContext.setAge(1);
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            if(countDownLatch != null){
                countDownLatch.countDown();
            }
        }
        return new AsyncResult<>("");
    }

    public void getName (ThreadContext threadContext) {
        threadContext.setName("name");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("异常", e);
        }
    }

    public void getAge (ThreadContext threadContext) {
        threadContext.setAge(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("异常", e);
        }
    }

}