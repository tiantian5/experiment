package com.experiment.core.service.talk.enums;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/3:49 下午
 * @Description: 发送类型
 */
public enum EnumSendType {

    /**
     * 文本
     */
    TEXT("文本", 1, new ArrayList<>() {{
        add(EnumSendPlatform.DING_DING_TALK.getType()); add(EnumSendPlatform.ENTERPRISE_WECHAT.getType());
    }}, "text"),

    /**
     * 图片
     */
    IMAGE("图片", 2, new ArrayList<>() {{
        add(EnumSendPlatform.ENTERPRISE_WECHAT.getType());
    }}, "image"),

    /**
     * 图文
     */
    IMAGE_TEXT("图文", 3, new ArrayList<>() {{
        add(EnumSendPlatform.ENTERPRISE_WECHAT.getType());
    }}, "news"),

    /**
     * 文件
     */
    FILE("文件", 4, new ArrayList<>() {{
        add(EnumSendPlatform.ENTERPRISE_WECHAT.getType());
    }}, "file"),

    /**
     * 链接
     */
    LINK("链接", 5, new ArrayList<>() {{
        add(EnumSendPlatform.DING_DING_TALK.getType());
    }}, "link"),

    ;

    /**
     * 发送名称
     */
    private String sendName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 适用于那些平台
     */
    private List<Integer> applyPlatFormList;

    /**
     * 类型 英文
     */
    private String sendNameEn;

    /**
     * 根据类型获取枚举
     *
     * @param type 类型
     * @return 枚举
     */
    public static EnumSendType getEnumSendTypeByType(Integer type) {
        if (type == null) {
            return null;
        }
        EnumSendType[] enumSendPlatforms = EnumSendType.values();
        for (EnumSendType enumSendPlatform : enumSendPlatforms) {
            if (enumSendPlatform.getType().equals(type)) {
                return enumSendPlatform;
            }
        }
        return null;
    }

    EnumSendType(String sendName, Integer type, List<Integer> applyPlatFormList, String sendNameEn) {
        this.sendName = sendName;
        this.type = type;
        this.applyPlatFormList = applyPlatFormList;
        this.sendNameEn = sendNameEn;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getApplyPlatFormList() {
        return applyPlatFormList;
    }

    public void setApplyPlatFormList(List<Integer> applyPlatFormList) {
        this.applyPlatFormList = applyPlatFormList;
    }

    public String getSendNameEn() {
        return sendNameEn;
    }

    public void setSendNameEn(String sendNameEn) {
        this.sendNameEn = sendNameEn;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(getEnumSendTypeByType(2)));
    }
}

