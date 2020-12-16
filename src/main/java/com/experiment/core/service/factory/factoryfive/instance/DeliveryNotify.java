package com.experiment.core.service.factory.factoryfive.instance;

import com.experiment.core.service.factory.factoryfive.NotifyCallBackParam;
import com.experiment.core.utils.SpringBeanUtils;
import lombok.SneakyThrows;


/**
 * @author tzw
 * @description
 * @create 2020-12-16 11:28 上午
 **/
public class DeliveryNotify extends BaseNotifyAction<NotifyCallBackParam> {

    private static final DeliveryNotify INSTANCE = new DeliveryNotify();

    /**
     * 获取到某个类的spring管理的bean
     */
    private Class aClass;

    private DeliveryNotify() {
        this.aClass = SpringBeanUtils.getBean(Class.class);
    }

    @Override
    @SneakyThrows
    public void realAction(NotifyCallBackParam notifyCallBackParam) {

        // 业务处理，也可以继续套用各种模式去处理

    }

    public static DeliveryNotify getInstance() { return INSTANCE; }
}
