package com.experiment.core.service.dutychain.chainone.handelchain;

import com.experiment.core.service.dutychain.chainone.context.DetailContext;
import org.springframework.stereotype.Component;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:31 下午
 **/

@Component
public class OneSceneHandelChainDetail extends AbstractDetailHandlerChain {

    @Override
    public Boolean progress(DetailContext detailContext) {

        autowiredBean();

        // doSomeThing

        return Boolean.TRUE;

    }

    @Override
    public void autowiredBean() {

    }

}