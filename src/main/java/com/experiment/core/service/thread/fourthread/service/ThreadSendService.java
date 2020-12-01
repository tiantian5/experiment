package com.experiment.core.service.thread.fourthread.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author tzw
 * @description
 * @create 2020-12-01 10:38 上午
 **/
public interface ThreadSendService {

    /**
     * 异步线程处理
     *
     * @param integerList 数据
     * @param countDownLatch countDownLatch
     */
    void threadDealDataToMarkTagForUserLabel(List<Integer> integerList, CountDownLatch countDownLatch);

}