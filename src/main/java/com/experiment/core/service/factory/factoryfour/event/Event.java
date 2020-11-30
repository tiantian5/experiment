package com.experiment.core.service.factory.factoryfour.event;

import com.experiment.core.enums.EnumEvent;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

/**
 * @author tzw
 * @description 品牌事件
 * @create 2020-10-02 9:26 下午
 **/

public class Event<T> extends ApplicationEvent {

    private static final Object DEFAULT_CONSTRUCTOR = new Object();

    /**
     * 事件枚举
     */
    private EnumEvent enumBrandEvent;

    /**
     * 涉及数据集合
     */
    private Collection<T> data;

    public Event(Object source, EnumEvent enumBrandEvent, Collection<T> data) {
        super(source);
        this.enumBrandEvent = enumBrandEvent;
        this.data = data;
    }

    public Event() {
        super(DEFAULT_CONSTRUCTOR);
    }

    public EnumEvent getEnumBrandEvent() {
        return enumBrandEvent;
    }

    public Event<T> setEnumBrandEvent(EnumEvent enumBrandEvent) {
        this.enumBrandEvent = enumBrandEvent;
        return this;
    }

    public Collection<T> getData() {
        return data;
    }

    public Event<T> setData(Collection<T> data) {
        this.data = data;
        return this;
    }
}