package com.experiment.core.service.template;

/**
 * @author tzw
 * @description 账户
 * @create 2020-12-02 10:43 上午
 **/

public abstract class Account {

    /**
     * 模板方法，计算利息数额
     *
     * <p>
     *     总体逻辑就是：
     *     1、获取子类特殊逻辑处理的结果
     *     2、根据模版获取到是某个子类
     *     3、父类抽取复用逻辑处理
     * </p>
     *
     * @return 返回利息数额
     */
    public final double calculateInterest(Long allAmount){

        double interestRate = doCalculateInterestRate();

        return calculateAmount(interestRate, allAmount);

    }

    /**
     * 基本方法留给子类实现
     *
     * @return Integer
     */
    protected abstract Integer doCalculateAccountType();

    /**
     * 基本方法留给子类实现
     *
     * @return double
     */
    protected abstract double doCalculateInterestRate();

    /**
     * 基本方法，已经实现
     */
    private double calculateAmount(double interestRate, Long allAmount){

        return interestRate * allAmount;

    }

}