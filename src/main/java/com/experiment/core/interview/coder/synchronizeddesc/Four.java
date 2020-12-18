package com.experiment.core.interview.coder.synchronizeddesc;

/**
 * @author tzw
 * @description
 * <p>
 *     场景四：两个线程分别同时访问（一个或两个）对象的同步方法和非同步方法
 *     两个线程其中一个访问同步方法，另一个访问非同步方法，此时程序会不会串行执行呢，也就是说是不是线程安全的呢？
 *     是线程不安全的，如果方法不加synchronized都是安全的，那就不需要同步方法了。
 *
 *     两个线程分别同时访问（一个或两个）对象的同步方法和非同步方法，是线程不安全的。
 *
 *     method1没有被synchronized修饰，所以不会受到锁的影响。即便是在同一个对象中，当然在多个实例中，更不会被锁影响了。
 *
 *     非同步方法不受其它由synchronized修饰的同步方法影响
 * </p>
 * @create 2020-12-18 11:13 上午
 **/
public class Four implements Runnable{

    static Four instance = new Four();

    @Override
    public void run() {
        // 两个线程访问同步方法和非同步方法
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行同步方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行非同步方法method1()
            method1();
        }
    }

    /**
     * 同步方法
     */
    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，同步方法，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，同步方法，运行结束");
    }

    /**
     * 非同步方法
     */
    private void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，普通方法，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，普通方法，运行结束");
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