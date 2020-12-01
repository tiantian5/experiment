package com.experiment.core.service.redislock;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 分布式锁切面，基于注解
 *
 * @author tzw
 */
@Aspect
public class DistributedLockAspect {

    private ExpressionParser parser = new SpelExpressionParser();
    private ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private DistributedLock distributedLock;

    private final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);

    public DistributedLockAspect(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        //获取增强的方法
        Method method = getMethod(joinPoint);
        //获取增强方法的参数
        Object[] parameterValues = joinPoint.getArgs();
        String[] spelDefineKeys = redisLock.keys();
        List<String> definitionKeyList = geDdefinitionKeyList(spelDefineKeys, method, parameterValues);
        String key =  redisLock.name() + StringUtils.collectionToDelimitedString(definitionKeyList,"","-","");
        if(StringUtils.isEmpty(key)){
            Object[] args = joinPoint.getArgs();
            key = Arrays.toString(args);
        }
        int retryTimes = redisLock.action().equals(RedisLock.LockFailAction.CONTINUE) ? redisLock.retryTimes() : 0;

        try {
            boolean lock = distributedLock.lock(key, retryTimes, redisLock.keepMills(), redisLock.sleepMills());
            if(!lock) {
                if (logger.isDebugEnabled()) {
                    logger.debug("get lock({}) failed : ", key);
                }
                throw new Exception("get lock failed");
            }
            //得到锁,执行方法，释放锁
            if (logger.isDebugEnabled()) {
                logger.debug("get lock({}) success : ", key);
            }
            return joinPoint.proceed();
        } finally {
            boolean releaseResult = distributedLock.releaseLock(key);
            if (logger.isDebugEnabled()) {
                logger.debug("release lock({}) : result {}", key, (releaseResult ? " success" : " failed"));
            }
        }
    }

    private List<String> geDdefinitionKeyList(String[] spelDefineKeys, Method method, Object[] parameterValues) {
        List<String> definitionKeyList = new ArrayList<>();
        for (String definitionKey : spelDefineKeys) {
            if (!StringUtils.isEmpty(definitionKey)) {
                EvaluationContext context = new MethodBasedEvaluationContext(new Object(), method, parameterValues, nameDiscoverer);
                Expression expression = parser.parseExpression(definitionKey);
                Object expressionValue = expression.getValue(context);
                if (expressionValue != null) {
                    String key = Objects.requireNonNull(expression.getValue(context)).toString();
                    definitionKeyList.add(key);
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("加锁key为:{}", JSON.toJSONString(definitionKeyList));
        }
        return definitionKeyList;
    }

    /**
     * 获取拦截的方法
     *
     * @param joinPoint 切入点
     * @return 拦截的方法
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),
                        method.getParameterTypes());
            } catch (Exception e) {
                logger.error("DistributedLockAspect.getMethod(), exception", e);
            }
        }
        return method;
    }

}
