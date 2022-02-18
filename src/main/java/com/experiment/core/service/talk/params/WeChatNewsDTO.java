package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/4:20 下午
 * @Description: 企业微信图文
 */

@Data
public class WeChatNewsDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 图文
     */
    private WeChatNews news;

    @Data
    public static class WeChatNews {

        /**
         * 图文集合
         */
        private List<Article> articles;

    }

    @Data
    public static class Article {

        /**
         * 标题
         */
        private String title;

        /**
         * 描述
         */
        private String description;

        /**
         * 路由
         */
        private String url;

        /**
         * 图片地址
         */
        private String picurl;

    }

}
