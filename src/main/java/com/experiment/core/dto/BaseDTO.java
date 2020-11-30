package com.experiment.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 10:32 上午
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDTO {

    /**
     * 分页参数
     */
    private Integer pageNo;

    /**
     * 分页参数
     */
    private Integer pageSie;

}