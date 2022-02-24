package com.experiment.core.service.invoke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/24/4:46 下午
 * @Description:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InConfigDTO {

    /**
     * 对应外部类的a b属性
     */
    private String testA;

    /**
     * 对应外部类的c属性
     */
    private String testB;

}
