package com.experiment.core.config;

import com.experiment.core.service.redislock.RedisDistributedLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author tzw
 * @description
 * @create 2020-12-01 3:53 下午
 **/

@Configuration
public class RedisConfiguration {

    private static final String LOCK_PRE_KEY = "lockPreKey";

    @Bean(name = "redisTemplate")
    RedisTemplate redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "connectionFactory")
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName("");
        jedisConnectionFactory.setPort(2000);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setDatabase(200);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxTotal(5);
        return jedisPoolConfig;
    }

    @Bean
    public RedisDistributedLock redisDistributedLock() {
        return new RedisDistributedLock(redisTemplate(), LOCK_PRE_KEY);
    }

//    @Value("${redisson.address}")
//    private String addressUrl;
//    @Value("${redisson.password}")
//    private String password;
//
//    @Bean(name = "redisson")
//    public RedissonClient getRedisson() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress(addressUrl).setPassword(password)
//                .setReconnectionTimeout(10000)
//                .setRetryInterval(5000)
//                .setTimeout(10000)
//                .setConnectTimeout(10000);
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }



}