package com.experiment.core.service.thread.onethread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tzw
 * @description 线程处理，需要执行结果
 * <p>
 *     1、将任务丢入线程中处理
 *     2、轮询超时时间内的业务处理情况
 *     3、异常情况处理
 * </p>
 *
 * @create 2020-11-30 2:51 下午
 **/

@Slf4j
@Service
public class OneNeedThreadResult {

    @Autowired
    private static ThreadPoolExecutor orderThreadPool;

    public static void main(String[] args) {

        // 将所需处理信息放入map或者list
        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.putIfAbsent("email", "12121");
        userInfoMap.putIfAbsent("phone", "21212");
        userInfoMap.putIfAbsent("name", "12121");

        // 线程处理返回值
        List<Future> futures = new ArrayList<>();
        userInfoMap.forEach((key, value) -> {
            Future<?> submit = orderThreadPool.submit(() -> {
                if (key.equals("email")) {
                    dealEmail(value);
                }
                if (key.equals("phone")) {
                    dealPhone(value);
                }
                if (key.equals("name")) {
                    dealName(value);
                }
            });
            futures.add(submit);
        });

        List<Object> isDoneList = new ArrayList<>();
        // 将已经完成的线程放入一个完成的集合中
        futures.forEach(e -> {
            try {
                while (true) {
                    if (e.isDone() && !e.isCancelled()) {
                        Object o = e.get();
                        // 先判空再强转
                        if (!Objects.isNull(o)) {
                            isDoneList.add(o);
                        }
                        break;
                    }
                    TimeUnit.MILLISECONDS.sleep(NumberUtils.LONG_ONE);
                }
            } catch (Exception fe) {
                log.error(fe.getMessage(), fe);
            }
        });

        // 判断是否在指定时间均已完成
        if (isDoneList.size() != userInfoMap.size()) {
            // 异常情况处理.......
        }

    }

    public static void dealEmail(String name) {
        // doSomething
    }
    public static void dealPhone(String name) {
        // doSomething
    }
    public static void dealName(String name) {
        // doSomething
    }

}