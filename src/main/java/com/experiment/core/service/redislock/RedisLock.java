package com.experiment.core.service.redislock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis分布式锁注解
 * <p>
 *     分布式锁名称、锁参数、最大持有时间、获取不到锁休眠时间、重试次数
 *
 * @author tzw
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 分布式锁名称
     */
    String name() default "default";

    /**
     * 分布式锁参数
     */
    String[] keys() default {};

    /**
     * 最大持有分布式锁时间，默认3s
     */
    long keepMills() default 3000L;

    /**
     * 获取不到锁休眠时间，默认200ms
     */
    long sleepMills() default 200L;

    /**
     * 获取分布式锁失败的最大重试次数，LockFailAction.CONTINUE为true，此参数才有效，默认3次
     */
    int retryTimes() default 3;

    /**
     * 获取分布式锁失败的下一步行为，默认获取锁失败重试
     */
    RedisLock.LockFailAction action() default LockFailAction.CONTINUE;

    enum LockFailAction {

        /**
         * 放弃执行
         */
        GIVEUP,

        /**
         * 继续执行
         */
        CONTINUE,
        ;
    }

}
