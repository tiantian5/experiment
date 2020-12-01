package com.experiment;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tzw
 * @description
 * @create 2020-11-27 5:00 下午
 **/

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages={ "com.experiment.core"})
@EnableTransactionManagement
@DubboComponentScan
@ComponentScan(basePackages = {"com.experiment.core"})
public class SpringBootDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo.class,args);
    }

}