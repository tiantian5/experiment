package com.experiment.core.service.factory.factoryone;

import com.experiment.core.dto.BaseDTO;

/**
 * @author tzw
 * @description 分发类
 * @create 2020-11-30 10:31 上午
 **/
public interface FactoryOneEventInterfaceHandler<T extends BaseDTO> {

    /**
     * 具体的业务处理分发器
     *
     * <p>
     *     可传入自适应类进行业务处理
     * </p>
     *
     * @param eventDTO 事件DTO
     */
    void handle(T eventDTO);

}