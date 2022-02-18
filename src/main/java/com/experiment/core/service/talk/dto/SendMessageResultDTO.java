package com.experiment.core.service.talk.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/27/2:25 下午
 * @Description:
 */

@Data
public class SendMessageResultDTO {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

}
