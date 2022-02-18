package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/1:33 下午
 * @Description: 钉钉链接信息
 */

@Data
public class DingDingLinkDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 链接
     */
    private DingDingLink link;

    @Data
    public static class DingDingLink {

        /**
         * 文本
         */
        private String text;

        /**
         * 标题
         */
        private String title;

        /**
         * 链接URL
         */
        private String picUrl;

        /**
         * 跳转路由
         */
        private String messageUrl;

    }

}
