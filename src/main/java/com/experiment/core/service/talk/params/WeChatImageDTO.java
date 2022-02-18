package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/4:06 下午
 * @Description: 企业微信图片
 */

@Data
public class WeChatImageDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 图片
     */
    private WeChatImage image;

    @Data
    public static class WeChatImage {

        /**
         * 图片转换为base64
         */
        private String base64;

        /**
         * 验证方式为md5
         */
        private String md5;

    }

}
