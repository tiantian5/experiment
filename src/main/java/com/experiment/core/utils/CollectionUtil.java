package com.experiment.core.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzw
 * @description list分组
 * @create 2020-02-20 12:18 下午
 **/
public class CollectionUtil {

    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T> <T>
     * @return <T>
     */
    public static <T> List<List<T>> averageAssignGroup(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        // (先计算出余数)
        int remainder = source.size() % n;
        // 然后是商
        int number = source.size() / n;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     */
    public static <T> List<List<T>> averageAssignList(List<T> sourcList, int size){
        if(CollectionUtils.isEmpty(sourcList)){
            return new ArrayList<>();
        }
        int count = sourcList.size() / size + (sourcList.size() % size> 0 ? 1 : 0) ;
        List<List<T>> resultList = new ArrayList<>(count);
        if(sourcList.size() <= size){
            resultList.add(sourcList);
        }else{
            for(int i = 0; i < count; i ++ ){
                List<T> temp;
                if(i == count - 1){
                    temp = sourcList.subList(i * size, sourcList.size());
                }else{
                    temp = sourcList.subList(i * size, (i + 1) * size);
                }
                if(!CollectionUtils.isEmpty(temp)){
                    resultList.add(temp);
                }
            }
        }
        return resultList;
    }

}