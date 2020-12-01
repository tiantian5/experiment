package com.experiment.core.service.redislock;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 *
 * @author tzw
 */
public class RedisDistributedLock extends AbstractDistributedLock {

    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    private final RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<Map<String, String>> releaseLockFlag = ThreadLocal.withInitial(Maps::newHashMap);

    private static final String UNLOCK_LUA = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

    private static final String KEY_PRE = ":LOCK_KEY:";

    private final String keyPrefix;

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate, String keyPrefix) {
        if (redisTemplate == null || keyPrefix == null) {
            throw new NullPointerException("RedisDistributedLock redisTemplate and keyPrefix is no allow null");
        }
        this.redisTemplate = redisTemplate;
        this.keyPrefix = keyPrefix + KEY_PRE;
    }

    @Override
    public boolean lock(String rawkey, int retryTimes, long expireMills, long sleepMillis) {
        String redisKey = keyPrefix + rawkey;
        boolean result = setRedis(redisKey, expireMills);
        //如果获取锁失败，按照传入的重试次数进行重试
        while (!result && retryTimes-- > 0) {
            try {
                logger.info("lock failed, retrying...{}", retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                logger.error("lock thread interrupted----------->key:{},exception:{}", rawkey, e);
                //重置线程中断标识
                Thread.currentThread().interrupt();
                return false;
            }
            result = setRedis(redisKey, expireMills);
        }
        return result;
    }

    @Override
    public boolean releaseLock(String rawkey) {
        final String redisKey = keyPrefix + rawkey;
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String value = releaseLockFlag.get().get(redisKey);
                if (value == null) {
                    return false;
                }
                return connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN ,1, redisKey.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
            };
            return redisTemplate.execute(callback);
        } catch (Exception e) {
            logger.error("release lock occur an exception", e);
        } finally {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            releaseLockFlag.remove();
        }
        return false;
    }

    private Boolean setRedis(final String redisKey, final long expireMills) {
        try {
            String uuidValue = UUID.randomUUID().toString();
            releaseLockFlag.get().put(redisKey, uuidValue);
            //原子的设置key与expireTime
            return redisTemplate.opsForValue().setIfAbsent(redisKey, uuidValue, expireMills, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("redis lock error.", e);
        }
        return false;
    }
}
