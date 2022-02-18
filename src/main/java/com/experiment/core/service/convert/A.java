package com.experiment.core.service.convert;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzw
 * @description
 * @create 2020-12-01 11:34 上午
 **/

@Data
public class A {

    private String name;

    private String age;

    private Boolean flag;

    private Date time;

    private Long longTime;

    private Integer integerTime;

    private BigDecimal bigDecimalTime;

    private Inside inside;

    @Data
    public static class Inside {
        private Integer integerInside;
        private Long aLongInside;
        private BigDecimal bigDecimalInside;
        private Date dateInside;
    }
}