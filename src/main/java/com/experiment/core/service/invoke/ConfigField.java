package com.experiment.core.service.invoke;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/24/4:41 下午
 * @Description:
 * <p>
 *     该功能主要是结合注解+反射的方式将多个字段的值进行处理后归纳至一个字段
 *     其实完全可以用多个class进行转换完成，但是这种方式利用了反射处理(看着高大上一点)，这样其实不利于性能
 *
 *     该场景用于：
 *     数据模型a 定义为存储一个json信息 eg：{"test1":"test1","test2":"test2","test3":"test3"}
 *     最外层dto class有多个字段供前端传入
 *     最底层do class有一个字段写入a
 * </p>
 */

@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField {
    String value();
}
