package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/1:35 下午
 * @Description: 钉钉图片
 */

@Data
public class DingDingImageDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    private DingDingImage image;

    @Data
    public static class DingDingImage {

        private String picUrl;

    }

}
