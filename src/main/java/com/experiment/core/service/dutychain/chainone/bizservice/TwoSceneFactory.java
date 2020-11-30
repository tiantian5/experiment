package com.experiment.core.service.dutychain.chainone.bizservice;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.dutychain.chainone.AbstractDetailStrategy;
import com.experiment.core.service.dutychain.chainone.context.DetailContext;
import com.experiment.core.service.dutychain.chainone.handelchain.BaseHandelChain;
import com.experiment.core.service.dutychain.chainone.handelchain.OneSceneHandelChain;
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
        BaseHandelChain baseHandelChain = new BaseHandelChain();

        baseHandelChain.setNextHandler(new OneSceneHandelChain());

        return baseHandelChain.handleMessage(new DetailContext());

    }

    @Override
    public Integer getType() {
        return EnumFactory.TWO_SCENE.getType();
    }
}