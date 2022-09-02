package com.experiment.core.service.highway;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/08/23/5:39 下午
 * @Description:
 */

@Data
public class SnPrintDTO implements Serializable {

    private String workOrderCode;

    private String sn;

    private String gmtCreate;

}
