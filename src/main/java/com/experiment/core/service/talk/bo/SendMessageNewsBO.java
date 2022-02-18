package com.experiment.core.service.talk.bo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/27/3:51 下午
 * @Description: 图文转换 {"title":"","description":"","url":"","picUrl":""}
 */

@Data
public class SendMessageNewsBO {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 图片地址
     */
    private String picUrl;

}
