package com.experiment.core.service.redislock;

import com.experiment.core.dto.BaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tzw
 * @description 示例使用，一般使用于DAO层
 * <p>
 *     redis+AOP实现的一层redis分布式锁
 *     1、对于mysql并发的去更改存库时可使用redis锁来达到库存单一的去被修改，防止超卖的情况出现。
 *     2、其他使用redis锁的场景
 * </p>
 * @create 2020-12-01 3:56 下午
 **/

@Service
@Slf4j
public class RedisLockService {

    @RedisLock(name = "updateBaseDTO", keys = {"#baseDTO.pageNo"})
    public Boolean updateBaseDTO(BaseDTO baseDTO) {
        if (baseDTO.getPageNo() == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}