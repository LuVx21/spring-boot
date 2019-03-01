package org.luvx.guava;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName: org.luvx.guava
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/2 16:37
 */
public class CacheCase {

    private Cache<String, String> poiCache = CacheBuilder.newBuilder()
            .maximumSize(1024 * 1024 * 1024)
            .build();

    private LoadingCache<String, String> poiCache1 = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String userId) {
                            return getUserName(userId);
                        }
                    });


    public String getUserNameById(final String userId) {
        String username = null;
        /// 缓存定义形式1
        try {
            username = poiCache.get(userId, () -> getUserName(userId));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /// 缓存定义形式2
        try {
            username = poiCache1.get(userId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 模拟耗时操作
     *
     * @param userId
     * @return username
     */
    private String getUserName(String userId) {
        System.out.println("执行耗时操作......");
        String username = null;

        switch (userId) {
            case "0101":
                username = "foo";
                break;
            case "0102":
                username = "bar";
                break;
        }

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.fromNullable(username).or("");
    }

    public static void main(String[] args) {
        CacheCase dao = new CacheCase();

        for (int i = 0; i < 3; i++) {
            System.out.println("--- " + i + " ---");
            System.out.println(dao.getUserNameById("0101"));
            System.out.println(dao.getUserNameById("0102"));
            System.out.println(dao.getUserNameById("0103"));
            System.out.println();
        }
    }
}
