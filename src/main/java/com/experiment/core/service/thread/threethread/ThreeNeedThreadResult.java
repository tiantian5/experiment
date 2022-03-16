package com.experiment.core.service.thread.threethread;

import com.experiment.core.service.thread.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public ThreadContext getIndexInfoForCountDownLatch() {

        long start = System.currentTimeMillis();
        // 只有一个有参的构造函数，且只能被赋值一次
        CountDownLatch countDownLatch = new CountDownLatch(2);
        List<Future<?>> task = new ArrayList<>();
        ThreadContext threadContext = new ThreadContext();
        try {
            // 一、name
            task.add(asyncDetailFacade.getName(threadContext, countDownLatch));

            // 二、age
            task.add(asyncDetailFacade.getAge(threadContext, countDownLatch));

            boolean wait = countDownLatch.await(1, TimeUnit.SECONDS);
            if(!wait){
                throw new RuntimeException("存在子线程运行超时");
            }
        } catch (Exception e) {
            task.forEach(item -> {
                if(item != null){
                    // 若该任务还没有结束，则通过cancel方法中断任务
                    item.cancel(true);
                }
            });
            log.error("聚合异常", e);
        }
        long end = System.currentTimeMillis();
        log.info("任务聚合使用同步工具总共耗时：{}ms", (end - start));
        return threadContext;

    }

    public ThreadContext getIndexInfoForGeneral() {

        long start = System.currentTimeMillis();
        ThreadContext threadContext = new ThreadContext();

        asyncDetailFacade.getName(threadContext);
        asyncDetailFacade.getAge(threadContext);

        long end = System.currentTimeMillis();
        log.info("任务聚合使用串行总共耗时：{}ms", (end - start));

        return threadContext;

    }

    static Map<String, Integer> map=new HashMap<>();
    private static final CountDownLatch COUNT_DOWN_LATCH =new CountDownLatch(4);
    public static void main(String[] args) {
        //记录开始时间
        long startTime=System.currentTimeMillis();
        Thread countUserThread=new Thread(() -> {
            try {
                System.out.println("正在统计新增用户数量");
                Thread.sleep(3000);
                map.put("userNumber",1);
                COUNT_DOWN_LATCH.countDown();
                System.out.println("统计新增用户数量完毕");
            } catch (InterruptedException e) {
                log.error("异常", e);
            }
        });
        Thread countOrderThread=new Thread(() -> {
            try {
                System.out.println("正在统计订单数量");
                Thread.sleep(3000);
                map.put("countOrder",2);
                COUNT_DOWN_LATCH.countDown();
                System.out.println("统计订单数量完毕");
            } catch (InterruptedException e) {
                log.error("异常", e);
            }
        });
        Thread countGoodsThread=new Thread(() -> {
            try {
                System.out.println("正在商品销量");
                Thread.sleep(3000);
                map.put("countGoods",3);
                COUNT_DOWN_LATCH.countDown();
                System.out.println("统计商品销量完毕");
            } catch (InterruptedException e) {
                log.error("异常", e);
            }
        });
        Thread countmoneyThread=new Thread(() -> {
            try {
                System.out.println("正在总销售额");
                Thread.sleep(3000);
                map.put("countmoney",4);
                COUNT_DOWN_LATCH.countDown();
                System.out.println("统计销售额完毕");
            } catch (InterruptedException e) {
                log.error("异常", e);
            }
        });
        //启动子线程执行任务
        countUserThread.start();
        countGoodsThread.start();
        countOrderThread.start();
        countmoneyThread.start();
        try {
            //主线程等待所有统计指标执行完毕
            COUNT_DOWN_LATCH.await();
            long endTime=System.currentTimeMillis();
            System.out.println("------统计指标全部完成--------");
            System.out.println("统计结果为："+map.toString());
            System.out.println("任务总执行时间为"+(endTime-startTime)/1000+"秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}