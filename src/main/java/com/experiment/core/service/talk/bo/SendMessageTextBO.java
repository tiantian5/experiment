package com.experiment.core.service.talk.bo;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/1:51 下午
 * @Description: 文本接受转换
 */

@Data
public class SendMessageTextBO {

    /**
     * 内容
     */
    private String context;

    /**
     * 需要关注的人电话
     */
    private List<String> phoneList;

    /**
     * 需要关注的人userId
     */
    private List<String> userIdList;

    /**
     * 是否通知所有人
     */
    private Boolean isAll;

}
