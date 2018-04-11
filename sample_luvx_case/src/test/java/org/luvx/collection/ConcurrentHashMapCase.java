package org.luvx.collection;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 底层:数据+链表+红黑树
 * 线程安全模型实现方式之一
 * CAS:Compare and Swap
 * 只在特定的地方使用synchronized
 */
public class ConcurrentHashMapCase {


    @Test
    public void run() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        // 调用putVal函数->计算hash值->数组为空则初始化
        map.put("a", "aa");
        map.put("b", "bb");
        System.out.println(map);
        // 对取得的值判断是否是树
        String a = map.get("a");

        // 同时指定key-value进行删除,不存在不会报异常
        map.remove("a", "aa");
    }
}
