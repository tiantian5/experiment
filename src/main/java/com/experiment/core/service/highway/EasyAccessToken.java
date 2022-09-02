package com.experiment.core.service.highway;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/08/15/3:39 下午
 * @Description:
 */

@Data
public class EasyAccessToken {
    private String accessToken;
    private String refreshToken;
    private String uid;
    private int expireTime;
}