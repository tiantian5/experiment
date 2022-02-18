package com.experiment.core.service.convert;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2021/11/12/4:25 下午
 * @Description:
 */
public class TestMain {

    public static void main(String[] args) {

        B b = new B();
        b.setAge("18");
        b.setName("123");
        b.setFlag(Boolean.FALSE);
        b.setBigDecimalTime(new BigDecimal("1.2"));
        b.setLongTime(1L);
        b.setTime(new Date());
        B.Inside inside = new B.Inside();
        inside.setALongInside(11L);
        inside.setDateInside(new Date());
        inside.setBigDecimalInside(new BigDecimal(1));
        b.setInside(inside);

        List<B> bList = new ArrayList<>();
        bList.add(b);

        // 单个转化
        A a = ClassConvert.FACTORY.getMapperFacade().map(b, A.class);
        System.out.println(JSONObject.toJSONString(a));

        // list转化
        List<A> aList = ClassConvert.FACTORY.getMapperFacade().mapAsList(bList, A.class);
        System.out.println(JSONObject.toJSONString(aList));

    }

}
