package org.luvx.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap实现缓存
 * http://www.importnew.com/19627.html
 */
public class CacheCase {

    static ConcurrentHashMap<Integer, Integer> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("f(" + i + ") = " + fibonacci(i));
        }
    }

    // Java7:使用了双重校验锁
    static int fibonacciJava7(int i) {
        if (i == 0 || i == 1)
            return i;

        Integer result = cache.get(i);
        if (result == null) {
            synchronized (cache) {
                result = cache.get(i);

                if (result == null) {
                    System.out.println("Slow calculation of " + i);

                    result = fibonacci(i - 2) + fibonacci(i - 1);
                    cache.put(i, result);
                }
            }
        }

        return result;
    }

    // Java8
    static int fibonacci(int i) {
        if (i == 0 || i == 1)
            return i;

        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("Slow calculation of " + key);

            return fibonacci(i - 2) + fibonacci(i - 1);
        });
    }

}
