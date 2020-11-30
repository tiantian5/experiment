package com.experiment;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tzw
 * @description
 * @create 2020-11-27 5:00 下午
 **/

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@DubboComponentScan
public class SpringBootDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo.class,args);
    }

}