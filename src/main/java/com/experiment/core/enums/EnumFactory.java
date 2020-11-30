package com.experiment.core.enums;

/**
 * @author tzw
 * @description 业务场景枚举
 * @create 2020-11-30 10:22 上午
 **/

public enum EnumFactory {

    /**
     * 场景一
     */
    ONE_SCENE(1, "场景一"),

    /**
     * 场景二
     */
    TWO_SCENE(2, "场景二"),

    /**
     * 场景三
     */
    THREE_SCENE(3, "场景三")
    ;

    private Integer type;

    private String note;

    EnumFactory(Integer type, String note) {
        this.type = type;
        this.note = note;
    }

    /**
     * 根据类型获取枚举
     *
     * @param eventType 类型
     * @return 枚举
     */
    public static EnumFactory getByType(Integer eventType) {
        for(EnumFactory enumFactory : EnumFactory.values()){
            if (enumFactory.getType().equals(eventType)) {
                return enumFactory;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

}