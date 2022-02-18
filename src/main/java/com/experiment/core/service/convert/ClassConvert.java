package com.experiment.core.service.convert;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @author tzw
 * @description bean之间的转化
 * <p>
 *     比较推荐orika这版的类转化，因为是底层字节码转变速度可观
 * </p>
 * @create 2020-12-01 11:31 上午
 **/

@Slf4j
public class ClassConvert {

    /**
     * 此处一定要static 不然会造成full-gc问题
     *
     * TODO 对应连接池 eg：jdbc、http等等连接池应用初始化client时一定要static或者单例化，否则会造成full-gc（重启可暂时解决该类问题）
     */
    public static final MapperFactory FACTORY = new DefaultMapperFactory.Builder().build();

    static{
        FACTORY.classMap(A.class, B.class)
                .exclude("")
                .byDefault()
                .customize(new CustomMapper<A, B>() {
                    @Override
                    public void mapAtoB(A a, B b, MappingContext context) {
                        try {
                        } catch (Exception e) {
                            log.error("A convert to B Failed,A={}", JSON.toJSONString(a), e);
                        }
                    }

                    @Override
                    public void mapBtoA(B a, A b, MappingContext context) {
                        try {
                        } catch (Exception e) {
                            log.error("B convert to A Failed,B={}", JSON.toJSONString(a), e);
                        }
                    }
                }).register();

        // 所有转化以此类推即可......

    }

}