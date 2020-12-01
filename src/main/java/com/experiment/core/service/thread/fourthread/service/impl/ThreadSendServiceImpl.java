package com.experiment.core.service.thread.fourthread.service.impl;

import com.experiment.core.service.thread.fourthread.service.ThreadSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author tzw
 * @description
 * @create 2020-12-01 10:42 上午
 **/

@Slf4j
@Service
public class ThreadSendServiceImpl implements ThreadSendService {

    @Override
    @Async("asyncServiceExecutor")
    public void threadDealDataToMarkTagForUserLabel(List<Integer> integerList, CountDownLatch countDownLatch) {

        try {
            int sum = integerList.stream().mapToInt(item -> item).sum();

            System.out.println(sum);
        } catch (Exception e) {
            log.error("。。。。。");
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

    }

}