package com.experiment.core.service.productiveconsumption.base;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author tzw
 * @description 可去重的消费者
 * @create 2020-12-01 5:45 下午
 **/
public class DeduplicatedConsumer<E> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String queueName;
    private final DeduplicatedQueue<E> queue;
    private final Consumer<E> consumer;
    private final String consumerName;
    private final ConsumerTask consumerTask = new ConsumerTask();
    private final int parallelism;
    private final ThreadPoolExecutor executor;
    /**
     * qps, 负值表示不限流
     */
    private final int qps;
    private final RRateLimiter rateLimiter;
    private volatile boolean running = false;

    /**
     * 创建可去重消费者
     *
     * @param redissonClient RedissonClient
     * @param queueName      队列名称
     * @param consumer       消费逻辑
     * @param parallelism    并发线程数量
     * @param qps            匀速QPS
     */
    private DeduplicatedConsumer(RedissonClient redissonClient, String queueName, Consumer<E> consumer, int parallelism, int qps) {
        this.queueName = queueName;
        this.queue = new RedissonDeduplicatedQueue<>(queueName, redissonClient);
        this.consumer = consumer;
        this.consumerName = queueName + "-consumer";
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(consumerName + "-%d")
                .build();
        this.parallelism = parallelism;
        this.executor = new ThreadPoolExecutor(
                parallelism,
                parallelism,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                threadFactory,
                new ThreadPoolExecutor.DiscardPolicy());
        this.qps = qps;
        if (this.qps > 0) {
            this.rateLimiter = redissonClient.getRateLimiter(queueName + ":rate");
            this.rateLimiter.delete();
            this.rateLimiter.trySetRate(RateType.OVERALL, this.qps, 1, RateIntervalUnit.SECONDS);
        } else {
            this.rateLimiter = null;
        }
    }

    public Consumer<E> getConsumer() {
        return consumer;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public int getParallelism() {
        return parallelism;
    }

    public int getQps() {
        return qps;
    }

    public boolean isRunning() {
        return running;
    }


    public static <E> Builder<E> builder(RedissonClient redissonClient, String queueName, Consumer<E> consumer) {
        return new Builder<>(redissonClient, queueName, consumer);
    }

    /**
     * 启动消费者服务
     */
    public void start() {
        running = true;
        for (int i = 0; i < parallelism; i++) {
            executor.execute(consumerTask);
        }
    }

    /**
     * 停止消费者服务
     */
    public void stop() {
        running = false;
        executor.shutdownNow();
    }

    /**
     * 向消费者服务添加一个元素
     *
     * @param element 元素
     */
    public void add(E element) {
        this.queue.add(element);
    }

    /**
     * 向消费者服务批量添加元素
     *
     * @param elements 元素集合
     */
    public void addAll(Collection<E> elements) {
        this.queue.addAll(elements);
    }

    private void consume(E element) {
        try {
            this.consumer.accept(element);
        } catch (Exception ex) {
            // 发生异常时，处理失败数据放入队列, 稍后重试
            queue.add(element);
            logger.error("消费元素: {} 异常, 重新放入队列: {}, 稍后重试", element, queueName, ex);
        }
    }

    public static class Builder<E> {
        private final RedissonClient redissonClient;
        private final String queueName;
        private final Consumer<E> consumer;
        private int parallelism = 1;
        private int qps = -1;


        private Builder(RedissonClient redissonClient, String queueName, Consumer<E> consumer) {
            this.redissonClient = redissonClient;
            this.queueName = queueName;
            this.consumer = consumer;
        }

        public Builder<E> parallelism(int parallelism) {
            this.parallelism = parallelism;
            return this;
        }

        public Builder<E> qps(int qps) {
            this.qps = qps;
            return this;
        }

        public DeduplicatedConsumer<E> build() {
            return new DeduplicatedConsumer<>(redissonClient, queueName, consumer, parallelism, qps);
        }
    }

    private class ConsumerTask implements Runnable {

        @Override
        public void run() {
            try {
                while (running) {
                    if (Objects.nonNull(rateLimiter)) {
                        rateLimiter.acquire();
                    }
                    E e = queue.take();
                    consume(e);
                }
                logger.info("{}任务结束", consumerName);
            } catch (InterruptedException e) {
                // 收到中断信号时恢复中断状态, 然后退出执行
                Thread.currentThread().interrupt();
                logger.info("{}线程被中断, 任务结束", consumerName);
            } catch (Exception e) {
                // 消费异常时, 打印异常日志, 重新提交任务
                logger.error("{}执行异常, 尝试重新提交任务", consumerName, e);
                executor.execute(consumerTask);
            }
        }
    }
}