package com.experiment.core.service.productiveconsumption;

import com.experiment.core.service.productiveconsumption.base.DeduplicatedConsumer;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;

/**
 * @author tzw
 * @description 库存校验器
 * @create 2020-12-01 5:21 下午
 **/

@Component
public class StockCalibrator {

    private DeduplicatedConsumer<Long> spuCalibrationService;

    // TODO 消费者主要是使用RedissonClient中的属性，测试该类需要在本地装载redis，在yml文件中配置连接、密码即可测试 且在RedisConfiguration配置类中初始化RedissonClient
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @PostConstruct
//    public void init() {
//
//        spuCalibrationService = DeduplicatedConsumer.<Long>builder(redissonClient, "StockCalibrator", spuId -> {
//                    // 业务处理 ...
//                    if (!this.calibration(spuId)) {
//                        // 处理失败的记录
//                        spuCalibrationService.add(spuId);
//                    }
//                })
//                .parallelism(1)
//                .build();
//        spuCalibrationService.start();
//    }

    @PreDestroy
    public void destroy() {
        spuCalibrationService.stop();
    }

    /**
     * 外部传入的数据进行处理
     *
     * @param skuIds skuIds
     */
    public void addChangedSkuIds(Collection<Long> skuIds) {
        // eg: 从mysql或者redis获取到一些需要校验或者处理的数据

        // 提交需要校准库存的spuId
        spuCalibrationService.addAll(skuIds);

    }

    Boolean calibration (Long supId) {
        if (supId.equals(1L)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}