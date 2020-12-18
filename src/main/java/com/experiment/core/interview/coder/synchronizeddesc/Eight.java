package com.experiment.core.interview.coder.synchronizeddesc;

/**
 * @author tzw
 * @description
 * <p>
 *     场景八：同步方法抛出异常后，JVM会自动释放锁的情况
 *     只有当同步方法执行完或执行时抛出异常这两种情况，才会释放锁。
 *
 *     在一个线程的同步方法中出现异常的时候，会释放锁，另一个线程得到锁，继续执行。而不会出现一个线程抛出异常后，
 *     另一个线程一直等待获取锁的情况。这是因为JVM在同步方法抛出异常的时候，会自动释放锁对象。
 *
 *     可以看出线程还是串行执行的，说明是线程安全的。而且出现异常后，不会造成死锁现象，JVM会自动释放出现异常线程的锁对象，其他线程获取锁继续执行。
 * </p>
 * @create 2020-12-18 11:29 上午
 **/
public class Eight implements Runnable {

    private static Eight instance = new Eight();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            // 线程0,执行抛异常方法method0()
            method0();
        }
        if (Thread.currentThread().getName().equals("Thread-1")) {
            // 线程1,执行正常方法method1()
            method1();
        }
    }

    private synchronized void method0() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //同步方法中，当抛出异常时，JVM会自动释放锁，不需要手动释放，其他线程即可获取到该锁
        System.out.println("线程名：" + Thread.currentThread().getName() + "，抛出异常，释放锁");
        throw new RuntimeException();

    }

    private synchronized void method1() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "，运行结束");
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