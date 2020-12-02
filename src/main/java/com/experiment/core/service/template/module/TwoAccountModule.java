package com.experiment.core.service.template.module;

import com.experiment.core.enums.EnumFactory;
import com.experiment.core.service.template.Account;

/**
 * @author tzw
 * @description
 * @create 2020-12-02 10:54 上午
 **/

public class TwoAccountModule extends Account {

    @Override
    protected Integer doCalculateAccountType() {
        return EnumFactory.TWO_SCENE.getType();
    }

    @Override
    protected double doCalculateInterestRate() {
        return 2.32d;
    }

}