package com.experiment.core.service.exportfile;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wusongsong
 * @date 2022/8/11
 */
@Setter
@Getter
public class CssStyleModel {
    private Integer fontSize;

    private int[] fontColorRgb;

    private Boolean bold;

    private Integer paddingTop;

    private Integer paddingBottom;

    private Integer paddingLeft;

    private Integer paddingRight;

    private String verticalAlign;

    private String horizontalAlign;


}
