package com.experiment.core.interview.coder.synchronizeddesc;

/**
 * @author tzw
 * @description
 * <p>
 *     场景二：两个线程同时访问两个对象的同步方法
 *     对象锁失效的场景，原因出在访问的是两个对象的同步方法，那么这两个线程分别持有的两个线程的锁，所以是互相不会受限的。
 *     加锁的目的是为了让多个线程竞争同一把锁，而这种情况多个线程之间不再竞争同一把锁，而是分别持有一把锁
 *
 *     两个线程同时访问两个对象的同步方法，是线程不安全的。
 *
 *
 *
 *     问题点：两个线程（thread1、thread2），访问两个对象（instance1、instance2）的同步方法（method()）,两个线程都有各自的锁，
 *     不能形成两个线程竞争一把锁的局势，所以这时，synchronized修饰的方法method()和不用synchronized修饰的效果一样
 *     （不信去把synchronized关键字去掉，运行结果一样），所以此时的method()只是个普通方法。
 *
 *     解决：若要使锁生效，只需将method()方法用static修饰，这样就形成了类锁，多个实例（instance1、instance2）共同竞争一把类锁，
 *     就可以使两个线程串行执行了。
 * </p>
 * @create 2020-12-18 11:00 上午
 **/
public class Two implements Runnable {

    // 创建两个不同的对象
    static Two instance1 = new Two();
    static Two instance2 = new Two();


    @Override
    public void run() {
        method();
    }

    private synchronized void method() {
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程名：" + Thread.currentThread().getName() + "，运行结束");
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);

        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println("测试结束");

    }

}