package com.experiment.core.enums;

/**
 * @author tzw
 * @description 品牌处理事件枚举
 * @create 2020-10-02 9:16 下午
 **/
public enum EnumEvent {

    /**
     * 品牌数据就绪获取商品数据事件
     */
    BRAND_GET_PITEM("brand_get_pitem"),

    /**
     * 品牌数据发生更新
     */
    BRAND_UPDATE("brand_update"),

    ;

    private final String tag;

    EnumEvent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}