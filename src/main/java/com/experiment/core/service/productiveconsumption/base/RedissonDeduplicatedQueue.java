package com.experiment.core.service.productiveconsumption.base;

import org.redisson.api.*;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author tzw
 * @description
 * Redis 4.0 Zset不支持 BZPOPMIN、BZPOPMAX等阻塞式命令。
 * 这里使用{@link RScoredSortedSet}去重有序, 使用{@link RBlockingQueue}阻塞式获取元素。
 * @create 2020-12-01 5:47 下午
 **/
public class RedissonDeduplicatedQueue<E> implements DeduplicatedQueue<E> {

    private final String queueName;
    private final RedissonClient redissonClient;
    private final RScoredSortedSet<E> queue;
    /**
     * 信号量, 唤醒take线程
     */
    private final RBlockingQueue<Integer> notEmpty;

    public RedissonDeduplicatedQueue(String queueName, RedissonClient redissonClient) {
        this.queueName = queueName;
        this.redissonClient = redissonClient;
        this.queue = redissonClient.getScoredSortedSet(queueName);
        this.notEmpty = redissonClient.getBlockingDeque(queueName + ":notEmpty");
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void add(E e) {
        if (queue.tryAdd(System.currentTimeMillis(), e)) {
            // 防止生产者过快导致notEmpty队列膨胀, 只在必要时添加信号量
            if (notEmpty.size() == 0) {
                notEmpty.offer(1);
            }
        }
    }

    @Override
    public void addAll(Collection<E> elements) {
        if (CollectionUtils.isEmpty(elements)) {
            return;
        }
        int size = elements.size();
        if (size == 1) {
            E ele = elements.iterator().next();
            add(ele);
            return;
        }
        BatchOptions options = BatchOptions.defaults().executionMode(BatchOptions.ExecutionMode.IN_MEMORY_ATOMIC);
        RBatch batch = redissonClient.createBatch(options);
        RScoredSortedSetAsync<E> queue = batch.getScoredSortedSet(queueName);
        double score = System.currentTimeMillis();
        for (E element : elements) {
            queue.tryAddAsync(score, element);
        }
        batch.execute();
        if (notEmpty.size() == 0) {
            notEmpty.offer(1);
        }
    }

    @Override
    public E poll() {
        return queue.pollFirst();
    }

    @Override
    public E take() throws InterruptedException {
        while (true) {
            E e = queue.pollFirst();
            if (e != null) {
                return e;
            }
            notEmpty.take();
        }
    }
}