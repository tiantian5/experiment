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
    public Future getName (ThreadContext threadContext, CountDownLatch countDownLatch) {
        try {
            threadContext.setName("name");
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            if(countDownLatch != null){
                countDownLatch.countDown();
            }
        }
        return new AsyncResult<>("");
    }

    @Async(value = "asyncServiceExecutor")
    public Future getAge (ThreadContext threadContext, CountDownLatch countDownLatch) {
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

}