package com.experiment.core.service.syncDutyChain;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadResetDemo {

    //1. Thread.interrupted()
    //2. InterruptedException

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            while(true){//默认是false  _interrupted state?
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("before:"+Thread.currentThread().isInterrupted());
                    Thread.interrupted(); //复位- 回到初始状态
                    System.out.println("after:"+Thread.currentThread().isInterrupted());
                }
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); //把isInterrupted设置成true
    }
}
