package com.experiment.core.service.hotcache;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/30/4:23 下午
 * @Description:
 */
@Getter
@Setter
public class NodeLRUCache {

    private HashMap<Integer, Node> map;

    private DoubleList cache;

    // 最大容量
    private int cap;

    public NodeLRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }


    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        // 将该数据提升为最近使用的
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }

        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }


    // API //

    /* 将某个 key 提升为最近使用的 */
    private void makeRecently(int key) {
        Node x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /* 添加最近使用的元素 */
    private void addRecently(int key, int val) {
        Node x = new Node(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /* 删除某一个 key */
    private void deleteKey(int key) {
        Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /* 删除最久未使用的元素 */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        Node deletedNode = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        int deletedKey = deletedNode.key;
        map.remove(deletedKey);
    }

    public static void main(String[] args) {
        NodeLRUCache nodeLRUCache = new NodeLRUCache(2);
        nodeLRUCache.put(1, 1);
        nodeLRUCache.put(1, 2);
        System.out.println(nodeLRUCache.get(1));
    }

}
