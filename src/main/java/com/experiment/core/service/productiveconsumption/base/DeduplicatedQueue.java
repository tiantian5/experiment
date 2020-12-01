package com.experiment.core.service.productiveconsumption.base;

import java.util.Collection;

/**
 * @author tzw
 * @description 可对元素去重的队列
 * @create 2020-12-01 5:46 下午
 **/
public interface DeduplicatedQueue<E> {

    /**
     * 元素总数
     *
     * @return 元素总数
     */
    int size();

    /**
     * 添加一个元素
     *
     * @param e 元素
     */
    void add(E e);

    /**
     * 批量添加元素
     *
     * @param elements 元素集合
     */
    void addAll(Collection<E> elements);

    /**
     * 移除并返回队首元素, 立即返回
     *
     * @return 队首元素, 可能为null
     */
    E poll();

    /**
     * 移除并返回队首元素, 队列为空时阻塞
     *
     * @return 队首元素
     * @throws InterruptedException 阻塞时被中断抛出
     */
    E take() throws InterruptedException;

}