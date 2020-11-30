package com.experiment.core.service.factory.factoryfour;

import com.experiment.core.enums.EnumEvent;
import com.experiment.core.service.factory.factoryfour.event.Event;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 11:42 上午
 **/
public interface EventListener<T> {

    /**
     * 处理的事件类型
     *
     * @return 处理的目标缓存事件类型
     */
    EnumEvent target();

    /**
     * 当指定类型的事件发生时的处理逻辑
     *
     * @param event 处理事件
     */
    void onEvent(Event<T> event);

}