package com.experiment.core.service.template.module;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.template.Account;

/**
 * @author tzw
 * @description
 * @create 2020-12-02 10:53 上午
 **/

public class OneAcountModule extends Account {

    @Override
    protected Integer doCalculateAccountType() {
        return EnumFactory.ONE_SCENE.getType();
    }

    @Override
    protected double doCalculateInterestRate() {
        return 1.2d;
    }

}