package com.experiment.core.service.hotcache;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/30/4:13 下午
 * @Description:
 */
@Getter
@Setter
public class Node {

    public int key, val;
    public Node next, prev;
    public Node(int k, int v) {
        this.key = k;
        this.val = v;
    }

}
