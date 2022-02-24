package com.experiment.core.service.invoke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/24/4:41 下午
 * @Description:
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutConfigDTO {

    @ConfigField("testA")
    private String a;

    @ConfigField("testA")
    private String b;

    @ConfigField("testB")
    private String c;

}
