package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;

public class GeoTest extends RedisAppTests {
    @Test
    void m0() {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
        String key = "geo-key";
        Point point = new Point(120, 31);
        geoOps.add(key, point, "foobar");
    }

    @Test
    void m1() {
        String key = "geo-key-1";
        Point point = new Point(120, 31);
        geoOps.add(key, point, "foobar");
    }
}
