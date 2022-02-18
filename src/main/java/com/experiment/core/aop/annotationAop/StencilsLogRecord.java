package com.experiment.core.aop.annotationAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/16/10:41 上午
 * @Description: 钢网日志记录注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface StencilsLogRecord {

    /**
     * 获取处理类型
     *
     * @return 返回类型
     */
    String code();

}
