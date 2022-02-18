package com.experiment.core.service.talk.service.context;

import com.experiment.core.service.talk.params.DingDingImageDTO;
import com.experiment.core.service.talk.params.DingDingLinkDTO;
import com.experiment.core.service.talk.params.DingDingTextDTO;
import com.experiment.core.service.talk.params.WeChatFileDTO;
import com.experiment.core.service.talk.params.WeChatImageDTO;
import com.experiment.core.service.talk.params.WeChatNewsDTO;
import com.experiment.core.service.talk.params.WeChatTextDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:02 下午
 * @Description:
 */

@Data
public class SendMessageContext {

    /**
     * 钉钉图片
     */
    private DingDingImageDTO dingDingImageDTO;

    /**
     * 钉钉链接
     */
    private DingDingLinkDTO dingDingLinkDTO;

    /**
     * 钉钉文本
     */
    private DingDingTextDTO dingDingTextDTO;

    /**
     * 微信文件
     */
    private WeChatFileDTO weChatFileDTO;

    /**
     * 微信图片
     */
    private WeChatImageDTO weChatImageDTO;

    /**
     * 微信图文
     */
    private WeChatNewsDTO weChatNewsDTO;

    /**
     * 微信文本
     */
    private WeChatTextDTO weChatTextDTO;

}
