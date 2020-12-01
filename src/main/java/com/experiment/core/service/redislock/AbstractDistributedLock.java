package com.experiment.core.service.redislock;

/**
 * 分布式锁抽象服务
 *
 * @author tzw
 */
public abstract class AbstractDistributedLock implements DistributedLock {

    /**
     * 最大持有分布式锁时间
     */
    static final long DEFAULT_TIMEOUT_MILLIS = 3000;

    /**
     * 获取分布式锁失败的最大重试次数
     */
    static final int DEFAULT_RETRY_TIMES = 3;

    /**
     * 获取不到锁休眠时间
     */
    static final long DEFAULT_SLEEP_MILLIS = 200;

    @Override
    public boolean lock(String key) {
        return lock(key, DEFAULT_RETRY_TIMES, DEFAULT_TIMEOUT_MILLIS, DEFAULT_SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes) {
        return lock(key, retryTimes, DEFAULT_TIMEOUT_MILLIS, DEFAULT_SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes, long sleepMillis) {
        return lock(key, retryTimes, DEFAULT_TIMEOUT_MILLIS, sleepMillis);
    }

    @Override
    public boolean lock(String key, long expireMills) {
        return lock(key, DEFAULT_RETRY_TIMES, expireMills, DEFAULT_SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, long expireMills, int retryTimes) {
        return lock(key, retryTimes, expireMills, DEFAULT_SLEEP_MILLIS);
    }
}
