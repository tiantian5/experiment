package com.experiment.core.service.dutychain.chainone.handelchain;

import com.experiment.core.service.dutychain.chainone.context.DetailContext;
import com.experiment.core.service.factory.factoryfour.instance.OneEventListener;
import com.experiment.core.utils.SpringBeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:21 下午
 **/

@Component
public class BaseHandelChain extends DetailAbstractHandlerChain {

    private OneEventListener oneEventListener;

    @Override
    public Boolean progress(DetailContext detailContext) {

        autowiredBean();

        // 获取业务基础信息，set至上下文中待下一个链路使用
        detailContext.setAge(25);

        return Boolean.TRUE;
    }

    @Override
    public void autowiredBean() {
        oneEventListener = SpringBeanUtils.getBean(OneEventListener.class);
    }

}