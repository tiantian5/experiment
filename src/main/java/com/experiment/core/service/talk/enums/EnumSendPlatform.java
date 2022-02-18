package com.experiment.core.service.talk.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/3:39 下午
 * @Description: 推送消息平台
 */

public enum EnumSendPlatform {

    /**
     * 钉钉
     */
    DING_DING_TALK("钉钉", 1),

    /**
     * 企业微信
     */
    ENTERPRISE_WECHAT("企业微信", 2),

    ;

    /**
     * 平台名称
     */
    private String platFormName;

    /**
     * 平台类型
     */
    private Integer type;

    /**
     * 根据类型获取枚举
     *
     * @param type 类型
     * @return 枚举
     */
    public static EnumSendPlatform getSendPlatformByType(Integer type) {
        if (type == null) {
            return null;
        }
        EnumSendPlatform[] enumSendPlatforms = EnumSendPlatform.values();
        for (EnumSendPlatform enumSendPlatform : enumSendPlatforms) {
            if (enumSendPlatform.getType().equals(type)) {
                return enumSendPlatform;
            }
        }
        return null;
    }

    EnumSendPlatform(String platFormName, Integer type) {
        this.platFormName = platFormName;
        this.type = type;
    }

    public String getPlatFormName() {
        return platFormName;
    }

    public void setPlatFormName(String platFormName) {
        this.platFormName = platFormName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
