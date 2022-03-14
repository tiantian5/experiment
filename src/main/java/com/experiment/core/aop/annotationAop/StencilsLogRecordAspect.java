package com.experiment.core.aop.annotationAop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/16/10:46 上午
 * @Description: 钢网日志处理AOP
 */

@Aspect
@EnableAspectJAutoProxy
@Component
@Slf4j
@Order(10)
public class StencilsLogRecordAspect {

    /**
     * 以自定义 @StencilsLogRecord 注解为切点
     */
//    @Pointcut("@annotation(com.experiment.core.aop.annotationAop.StencilsLogRecord)")
//    public void annotation() {
//
//    }

//    @After("annotation()")
    public void dealStencilsLogRecord(JoinPoint point) {

//        StencilsLogRecord exportLock = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(StencilsLogRecord.class);
//        StencilsLogActionEnum enumByCode = exportLock.enumInfo();
//        String code = enumByCode.getCode();
//
//        // 获取参数 约定第一个参数为钢网信息表的主键ID
//        Object[] args = point.getArgs();
//        Long id = (Long)args[0];
//        if (id == null) {
//            log.error("钢网日志处理主键ID为空，请检查");
//            return;
//        }
//
    }

}
