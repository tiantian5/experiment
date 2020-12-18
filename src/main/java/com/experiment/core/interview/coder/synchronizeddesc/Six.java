package com.experiment.core.interview.coder.synchronizeddesc;

/**
 * @author tzw
 * @description
 * <p>
 *     场景六：两个线程同时访问同一个对象的不同的同步方法
 *
 *     两个线程同时访问同一个对象的不同的同步方法时，是线程安全的。
 *
 *     两个方法（method0()和method1()）的synchronized修饰符，虽没有指定锁对象，但默认锁对象为this对象为锁对象，
 *     所以对于同一个实例（instance），两个线程拿到的锁是同一把锁，此时同步方法会串行执行。这也是synchronized关键字的可重入性的一种体现。
 * </p>
 * @create 2020-12-18 11:24 上午
 **/
public class Six implements Runnable{

    static Six instance = new Six();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行同步方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行同步方法method1()
            method1();
        }
    }

    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法0，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，同步方法0，运行结束");
    }

    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法1，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，同步方法1，运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {
        }
        System.out.println("测试结束");
    }

}