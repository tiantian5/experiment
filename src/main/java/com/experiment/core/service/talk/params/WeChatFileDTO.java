package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/5:04 下午
 * @Description: 企业微信文件
 */

@Data
public class WeChatFileDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 文件
     */
    private WeChatFile file;

    @Data
    public static class WeChatFile {

        /**
         * 素材ID
         */
        private String media_id;

    }


}
