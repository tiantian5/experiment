package com.experiment.core.service.dutychain.chainone;

import com.experiment.core.service.dutychain.chainone.context.DetailContext;
import com.experiment.core.service.dutychain.chainone.handelchain.BaseHandelChain;
import com.experiment.core.service.dutychain.chainone.handelchain.OneSceneHandelChain;
import com.experiment.core.service.dutychain.chainone.handelchain.TwoSceneHandelChain;

/**
 * @author tzw
 * @description 策略实现父类
 * @create 2020-11-30 2:16 下午
 **/
public abstract class AbstractDetailStrategy implements DetailStrategy {

    /**
     * 父类基础链路
     * 场景一 -- > 场景二 -> 场景三 -> ......
     *
     * @param obj 请求入参
     * @return 上下文
     */
    @Override
    public Object getDetail(Object... obj) {

        BaseHandelChain baseHandelChain = new BaseHandelChain();

        baseHandelChain.setNextHandler(new OneSceneHandelChain()).
                setNextHandler(new TwoSceneHandelChain());

        return baseHandelChain.handleMessage(new DetailContext());

    }

}