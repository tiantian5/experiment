package com.experiment.core.service.dutychain.chainone.bizservice;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.dutychain.chainone.AbstractDetailStrategy;
import com.experiment.core.service.dutychain.chainone.context.DetailContext;
import com.experiment.core.service.dutychain.chainone.handelchain.BaseHandelChainDetail;
import com.experiment.core.service.dutychain.chainone.handelchain.OneSceneHandelChainDetail;
import org.springframework.stereotype.Service;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:38 下午
 **/

@Service
public class TwoSceneFactory extends AbstractDetailStrategy {

    @Override
    public Object getDetail(Object... obj) {

        // 只需基础链路+链路一
        BaseHandelChainDetail baseHandelChain = new BaseHandelChainDetail();

        baseHandelChain.setNextHandler(new OneSceneHandelChainDetail());

        return baseHandelChain.handleMessage(new DetailContext());

    }

    @Override
    public Integer getType() {
        return EnumFactory.TWO_SCENE.getType();
    }
}