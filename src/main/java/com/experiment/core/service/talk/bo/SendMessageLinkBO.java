package com.experiment.core.service.talk.bo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/2:05 下午
 * @Description: 链接接受转换
 */

@Data
public class SendMessageLinkBO {

    /**
     * 文本
     */
    private String text;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 路由
     */
    private String messageUrl;

}
