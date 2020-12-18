package com.experiment.core.interview.coder.synchronizeddesc;

/**
 * @author tzw
 * @description
 * <p>
 *     场景七：两个线程分别同时访问静态synchronized和非静态synchronized方法
 *
 *     静态synchronized方法属于类锁，锁对象是（*.class）对象，非静态synchronized方法属于对象锁中的方法锁，锁对象是this对象。
 *     两个线程拿到的是不同的锁，自然不会相互影响
 *
 *     两个线程分别同时访问静态synchronized和非静态synchronized方法，线程不安全。
 * </p>
 * @create 2020-12-18 11:26 上午
 **/
public class Senve implements Runnable {

    static Senve instance = new Senve();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行静态同步方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行非静态同步方法method1()
            method1();
        }
    }

    /**
     * 重点：用static synchronized 修饰的方法，属于类锁，锁对象为（*.class）对象。
     */
    private static synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，静态同步方法0，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，静态同步方法0，运行结束");
    }

    /**
     * 重点：synchronized 修饰的方法，属于方法锁，锁对象为（this）对象。
     */
    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，非静态同步方法1，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，非静态同步方法1，运行结束");
    }

    public static void main(String[] args) {
        // 问题原因： 线程1的锁是类锁（*.class）对象，线程2的锁是方法锁（this）对象,两个线程的锁不一样，自然不会互相影响，所以会并行执行。
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {
        }
        System.out.println("测试结束");
    }

}