package org.luvx.cache;

import java.util.*;

public class LFUCache {
    static class Value implements Comparable<Value> {    //定义一个静态内部类，主要是用于统计命中数
        Object key;
        Object val;
        int hitCount;

        public Value(Object v, Object key) {
            this.key = key;
            this.val = v;
            this.hitCount = 1;  //第一次进入设置命中为1
        }

        public void setVal(Object obj) {
            this.val = obj;
        }

        public void countInc() {
            hitCount++;
        }

        @Override
        public int compareTo(Value o) {
            if (o instanceof Value) {  //如果比较的类属于Value或者是Value的子类
                Value v = (Value) o;
                if (this.hitCount > v.hitCount)
                    return 1;
                else
                    return -1;
            }
            return 0;
        }


    }

    final int SIZE;
    private Map<Object, Value> map = new HashMap<>();

    public LFUCache(int size) {
        SIZE = size;
    }

    //存储数据
    public void put(Object k, Object v) {
        //如果本来就存在
        if (map.get(k) != null) {
            map.get(k).countInc();//命中计数
            map.get(k).setVal(v);//覆盖结果值
        } else {
            //如果存储已超过限定值
            if (map.size() >= SIZE) {
                remove();//移除最后一个数据
            }
            Value value = new Value(v, k);
            map.put(k, value);
        }
    }

    //获取缓存中的数据
    public Object get(Object k) {
        if (k == null)
            return null;

        //命中+1
        map.get(k).countInc();
        return map.get(k).val;
    }

    //数据移除最后一个数据
    public void remove() {
        //先拿到最后一个数据
        Value v = Collections.min(map.values());
        //移除最后一个数据
        map.remove(v.key);
    }

    //获取存储情况
    public String showList() {
        List<Value> list = new ArrayList<Value>();
        list.addAll(map.values());
        Collections.sort(list);
        String result = "";
        for (Value value : list) {
            result += value.key + "=" + value.val + "  ";
        }
        return result;
    }


}