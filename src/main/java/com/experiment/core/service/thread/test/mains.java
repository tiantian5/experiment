package com.experiment.core.service.thread.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/05/05/2:48 下午
 * @Description:
 */
public class mains {

    public static void main(String[] args) {

        A a = new A();
        a.setAge(1);

        List<B> bList = new ArrayList<>();
        B b = new B();
        b.setName("1s");
        bList.add(b);

        a.setBList(bList);

        C c = copy(a, C.class);

        System.out.println(JSONObject.toJSONString(c));

    }

    public static <T, S> T copy(S s, Class<T> clazz) {
        if (null == s) {
            return null;
        }
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(s, t);
        } catch (Exception e) {

        }
        return t;
    }


}
