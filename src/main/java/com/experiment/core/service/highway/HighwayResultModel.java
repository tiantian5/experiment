package com.experiment.core.service.highway;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/08/15/3:39 下午
 * @Description:
 */

@Data
public class HighwayResultModel<T> {
    private Boolean success;
    private Long t;
    private T result;
    private Integer code;
    private String msg;

    @JsonCreator
    public HighwayResultModel(@JsonProperty("success") Boolean success, @JsonProperty("t") Long t, @JsonProperty("result") T result,
                              @JsonProperty("code") Integer code, @JsonProperty("msg") String msg) {
        this.success = success;
        this.t = t;
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

}
