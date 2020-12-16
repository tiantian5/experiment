package com.experiment.core.interview.code;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tzw
 * @description 实现阻塞队列
 *
 * <p>
 *
 *     一、利用生产消费者实现阻塞队列
 *     二、队列：特殊的线性表，只允许在表的前端（front）进行删除操作，而在表的后端（rear）进行插入操作。和栈最大的区别就是队列是先进先出
 *     当队列为空时，消费者挂起，队列已满时，生产者挂起，这就是生产-消费者模型。堵塞队列会通过挂起的方式来实现生产者和消费者之间的平衡
 *     BlockingQueue就是jdk提供的阻塞队列
 *
 *     BlockingQueue的一些操作
 *     1、插入数据
 *        offer(E e)：如果队列没满，返回true，如果队列已满，返回false（不堵塞）
 *        offer(E e, long timeout, TimeUnit unit)：可以设置等待时间，如果队列已满，则进行等待。超过等待时间，则返回false
 *        put(E e)：无返回值，一直等待，直至队列空出位置
 *     2、获取数据
 *        poll()：如果有数据，出队，如果没有数据，返回null
 *        poll(long timeout, TimeUnit unit)：可以设置等待时间，如果没有数据，则等待，超过等待时间，则返回null
 *        take()：如果有数据，出队。如果没有数据，一直等待（堵塞）
 *
 *     主要有ArrayBlockingQueue、LinkedBlockingQueue、DelayQueue、PriorityBlockingQueue、SynchronousQueue
 *     - ArrayBlockingQueue：基于数组实现的，通过初始化时设置数组长度，是一个有界队列，而且ArrayBlockingQueue和LinkedBlockingQueue不同的是，
 *                         ArrayBlockingQueue只有一个锁对象，而LinkedBlockingQueue是两个锁对象，一个锁对象会造成要么是生产者获得锁，
 *                         要么是消费者获得锁，两者竞争锁，无法并行。
 *     - LinkedBlockingQueue：基于链表实现的，和ArrayBlockingQueue不同的是，大小可以初始化设置，如果不设置，默认设置大小为Integer.MAX_VALUE，
 *                         LinkedBlockingQueue有两个锁对象，可以并行处理。
 *     - DelayQueue：基于优先级的一个无界队列，队列元素必须实现Delayed接口，支持延迟获取，元素按照时间排序，只有元素到期后，消费者才能从队列中取出。
 *     - PriorityBlockingQueue：基于优先级的一个无界队列，底层是基于数组存储元素的，元素按照优选级顺序存储，优先级是通过Comparable的compareTo方法来实现的（自然排序），
 *                         和其他堵塞队列不同的是，其只会堵塞消费者，不会堵塞生产者，数组会不断扩容，使用时要谨慎。
 *     - SynchronousQueue：一个特殊的队列，其内部是没有容器的，所以生产者生产一个数据，就堵塞了，必须等消费者消费后，生产者才能再次生产，
 *                         称其为队列有点不合适，现实生活中，多个人才能称为队，一个人称为队有些说不过去。
 *
 *
 *     三、Condition:提供了类似Object的监视器方法，与Lock配合可以实现等待/通知模式。condition对象需要通过lock对象进行创建出来(调用Lock对象的newCondition()方法)
 *     当前线程加入Condition等待队列中。当前线程释放锁。否则别的线程就无法拿到锁而发生死锁。自旋(while)挂起，不断检测节点是否在同步队列中了，如果是则尝试获取锁，
 *     否则挂起。当线程被signal方法唤醒，被唤醒的线程将从await()方法中的while循环中退出来，然后调用acquireQueued()方法竞争同步状态。
 *
 * </p>
 *
 * @create 2020-12-16 5:26 下午
 **/

@Slf4j
@Data
public class BlockQueue {

    /**
     * 队列容器
     */
    private Object[] tab;

    /**
     * 出队下标
     */
    private int takeIndex;

    /**
     * 入队下标
     */
    private int putIndex;

    /**
     * 元素数量
     */
    private int size;

    private ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 读条件
     */
    private Condition notEmpty;

    /**
     * 写条件
     */
    private Condition notFull;

    /**
     * 初始化构造器中信息
     *
     * @param tabCount 初始化大小
     */
    public BlockQueue(int tabCount) {

        if (tabCount <= 0) {
            log.error("。。。。。");
            return;
        }

        tab = new Object[tabCount];
        notEmpty = reentrantLock.newCondition();
        notFull = reentrantLock.newCondition();

    }

    /**
     * 写入
     *
     * @param obj obj
     * @return boolean
     */
    public boolean offer(Object obj) {
        if (obj == null) { throw new NullPointerException(); }

        // 获取锁
        reentrantLock.lock();
        try {
            // 队列已满
            while (size == tab.length){
                System.out.println("队列已满");
                // 堵塞
                notFull.await();
            }
            tab[putIndex] = obj;
            if(++putIndex == tab.length) {
                putIndex = 0;
            }
            size ++;
            // 唤醒读线程
            notEmpty.signal();
            return true;
        } catch (Exception e) {
            //唤醒读线程
            notEmpty.signal();
        } finally {
            reentrantLock.unlock();
        }
        return false;
    }

    /**
     * 读出
     *
     * @return Object
     */
    public Object take(){

        reentrantLock.lock();

        try {
            while (size == 0){
                System.out.println("队列空了");
                // 堵塞
                notEmpty.await();
            }
            Object obj = tab[takeIndex];
            //如果到了最后一个，则从头开始
            if(++takeIndex == tab.length){
                takeIndex = 0;
            }
            size --;
            // 唤醒写线程
            notFull.signal();
            return obj;
        }catch (Exception e){
            // 唤醒写线程
            notFull.signal();
        }finally {
            reentrantLock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        BlockQueue yzBlockingQuery=new BlockQueue(5);
        Thread thread1 = new Thread(() -> {
            for (int i = 0;i < 100;i ++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error(".....");
                }
                yzBlockingQuery.offer(i);
                System.out.println("生产者生产了：" + i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0;i < 100;i ++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error(".....");
                }
                Object take = yzBlockingQuery.take();
                System.out.println("消费者消费了：" + take);
            }
        });

        thread1.start();
        thread2.start();
    }

}