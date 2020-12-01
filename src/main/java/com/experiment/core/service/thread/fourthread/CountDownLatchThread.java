package com.experiment.core.service.thread.fourthread;

import com.experiment.core.service.thread.fourthread.service.ThreadSendService;
import com.experiment.core.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author tzw
 * @description 利用CountDownLatch+异步线程来处理一些大批量的数据，可以减少执行时间
 *
 * <p>
 *     测试数据过于简单 测试时间差距不是特别大
 *
 *     CountDownLatch亦可替换为循环栅栏、信号量等控制线程支持，按需所定
 * </p>
 *
 * @create 2020-12-01 10:25 上午
 **/

@Slf4j
@Component
public class CountDownLatchThread {

    private static final Integer MAX = 1000000;

    @Resource
    private ThreadSendService threadSendService;

    /**
     * 准备测试数据 100w个
     */
    private static ArrayList<Integer> integerList = new ArrayList<Integer>() {{
        for (int i = 0; i < MAX; i++) {
            add(i);
        }
    }};

    public void dealNumberForOneThread() {
        long startTime = System.currentTimeMillis();

        int sum = integerList.stream().mapToInt(item -> item).sum();

        System.out.println(sum);

        long endTime = System.currentTimeMillis();

        log.info("单个线程处理100w条数据耗时：{}", (endTime - startTime));
    }

    public void dealNumberForUnSyncThread () {

        long startTime = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(100);
        // 将100w数据100等份
        List<List<Integer>> lists = CollectionUtil.averageAssignGroup(integerList, 100);
        lists.forEach(itemList ->{
            if (!CollectionUtils.isEmpty(itemList)) {
                threadSendService.threadDealDataToMarkTagForUserLabel(itemList, countDownLatch);
            }
        });

        try {
            // 等待指定时间，可以使主线程等待子线程时长
            boolean await = countDownLatch.await(3, TimeUnit.MINUTES);
            log.info("处理结果，await is {}", await);
        } catch (InterruptedException e) {
            log.error("。。。。");
        }

        long endTime = System.currentTimeMillis();

        log.info("异步线程处理100w条数据耗时：{}", (endTime - startTime));

    }

}