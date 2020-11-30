package com.experiment.core.service.factory.factoryone;

import com.experiment.core.enums.EnumFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author tzw
 * @description 注解
 * @create 2020-11-30 10:28 上午
 **/

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface FactoryOneEventConstructor {

    /**
     * 获取枚举
     *
     * @return 枚举
     */
    EnumFactory value();

}