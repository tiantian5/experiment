package com.experiment.core.service.redislock;

/**
 * 分布式锁接口
 *
 * @author tzw
 */
public interface DistributedLock {

    /**
     * 加分布式锁，持有锁时间、获取不到锁休眠时间、重试次数都是默认值
     *
     * @param key 分布式锁key
     * @return {@code true}加分布式锁成功
     * @see AbstractDistributedLock#DEFAULT_RETRY_TIMES
     * @see AbstractDistributedLock#DEFAULT_SLEEP_MILLIS
     * @see AbstractDistributedLock#DEFAULT_TIMEOUT_MILLIS
     */
    boolean lock(String key);

    /**
     * 加分布式锁，持有锁时间、获取不到锁休眠时间是默认值
     *
     * @param key 分布式锁key
     * @param retryTimes 加分布式失败最大重试次数
     * @return {@code true}加分布式锁成功
     * @see AbstractDistributedLock#DEFAULT_SLEEP_MILLIS
     * @see AbstractDistributedLock#DEFAULT_TIMEOUT_MILLIS
     */
    boolean lock(String key, int retryTimes);

    /**
     * 加分布式锁，持有锁时间是默认值
     *
     * @param key 分布式锁key
     * @param retryTimes 加分布式失败最大重试次数
     * @param sleepMillis 获取不到锁休眠时间
     * @return {@code true}加分布式锁成功
     * @see AbstractDistributedLock#DEFAULT_TIMEOUT_MILLIS
     */
    boolean lock(String key, int retryTimes, long sleepMillis);

    /**
     * 加分布式锁，获取不到锁休眠时间、重试次数是默认值
     *
     * @param key 分布式锁key
     * @param expireMills 持有锁时间
     * @return {@code true}加分布式锁成功
     * @see AbstractDistributedLock#DEFAULT_RETRY_TIMES
     * @see AbstractDistributedLock#DEFAULT_SLEEP_MILLIS
     */
    boolean lock(String key, long expireMills);

    /**
     * 加分布式锁，获取不到锁休眠时间是默认值
     *
     * @param key 分布式锁key
     * @param expireMills 持有锁时间
     * @param retryTimes 重试次数
     * @return {@code true}加分布式锁成功
     * @see AbstractDistributedLock#DEFAULT_SLEEP_MILLIS
     */
    boolean lock(String key, long expireMills, int retryTimes);

    /**
     * 加分布式锁
     *
     * @param key 分布式锁key
     * @param retryTimes 重试次数
     * @param expireMills 持有锁时间
     * @param sleepMillis 获取不到锁休眠时间
     * @return {@code true}加分布式锁成功
     */
    boolean lock(String key, int retryTimes, long expireMills, long sleepMillis);

    /**
     * 释放分布式锁
     *
     * @param key 分布式锁key
     * @return {@code true}释放分布式锁成功
     */
    boolean releaseLock(String key);
}
