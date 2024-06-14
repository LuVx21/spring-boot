package org.luvx.boot.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * RedisTemplate 装饰器
 */
public class RedisTemplateHolder {
    /**
     * 最大有20个redis连接被使用，其他的连接要等待令牌释放
     * 令牌数量自己定义，这个令牌是为了避免高并发下，获取redis连接数时，抛出的异常
     * 在压力测试下，性能也很可观
     */
    private static final Semaphore semaphore = new Semaphore(20);

    private RedisTemplateHolder() {
    }

    public static RedisTemplate<?, ?> getRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
        try {
            semaphore.acquire();
            return redisTemplate;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void release() {
        semaphore.release();
    }

    public static Object execute(Function<RedisTemplate<?, ?>, Object> statement, RedisTemplate<?, ?> redisTemplate) {
        try {
            return statement.apply(getRedisTemplate(redisTemplate));
        } finally {
            RedisTemplateHolder.release();
        }
    }
}
