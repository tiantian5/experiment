package com.experiment.core.controller;

import com.experiment.core.dto.BaseDTO;
import com.experiment.core.enums.EnumEvent;
import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.factory.factoryfour.event.Event;
import com.experiment.core.service.factory.factoryone.FactoryOneEventInterfaceHandler;
import com.experiment.core.service.factory.factoryone.OneEventFactory;
import com.experiment.core.service.factory.factorythree.ThreeFactoryServiceDeal;
import com.experiment.core.service.factory.factorytwo.TwoStrategyFactory;
import com.experiment.core.service.productiveconsumption.StockCalibrator;
import com.experiment.core.service.thread.fourthread.CountDownLatchThread;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author tzw
 * @description 测试
 * @create 2020-11-30 11:01 上午
 **/

@EnableAutoConfiguration
@Controller
public class TestController {

    @Resource
    private OneEventFactory oneEventFactory;

    @Resource
    private TwoStrategyFactory twoStrategyFactory;

    @Resource
    private ThreeFactoryServiceDeal threeFactoryServiceDeal;

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private CountDownLatchThread countDownLatchThread;

    @Resource
    private StockCalibrator stockCalibrator;

    /**
     * 工厂一 测试类
     */
    @RequestMapping("/oneFactoryTest")
    @ResponseBody
    public void oneFactoryTest(){

        FactoryOneEventInterfaceHandler factoryOneEventInterfaceHandler = oneEventFactory.getEventHandler(EnumFactory.ONE_SCENE.getType());
        if (factoryOneEventInterfaceHandler == null) {
            return;
        }
        factoryOneEventInterfaceHandler.handle(new BaseDTO());

    }

    /**
     * 工厂二 测试类
     */
    @RequestMapping("/twoFactoryTest")
    @ResponseBody
    public void twoFactoryTest(){

        twoStrategyFactory.getDealFactoryTwoStrategy(EnumFactory.ONE_SCENE.getType()).dealHandel(new BaseDTO());

    }

    /**
     * 工厂三 测试类
     */
    @RequestMapping("/threeFactoryTest")
    @ResponseBody
    public void threeFactoryTest(){

        threeFactoryServiceDeal.deal();

    }

    /**
     * 工厂四 测试类
     */
    @RequestMapping("/fourFactoryTest")
    @ResponseBody
    public void fourFactoryTest(){

        this.publisher.publishEvent(new Event<>(this, EnumEvent.BRAND_GET_PITEM, Collections.singleton("")));

    }

    /**
     * 线程 测试类
     */
    @RequestMapping("/threadTest")
    @ResponseBody
    public void threadTest(){

        countDownLatchThread.dealNumberForOneThread();

        countDownLatchThread.dealNumberForUnSyncThread();

    }

    /**
     * 生产消费 测试类
     */
    @RequestMapping("/coustomTest")
    @ResponseBody
    public void coustomTest(){

        ArrayList<Long> spuIds = new ArrayList<Long>(){{
            add(1L);
            add(2L);
            add(3L);
        }};
        stockCalibrator.addChangedSkuIds(spuIds);

    }

}