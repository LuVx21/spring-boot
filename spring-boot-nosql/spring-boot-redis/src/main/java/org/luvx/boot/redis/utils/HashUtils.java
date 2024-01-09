package org.luvx.boot.redis.utils;

import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

public class HashUtils {
    public static final Jackson2HashMapper FLATTEN_MAPPER = new Jackson2HashMapper(true);
    public static final ObjectHashMapper   OBJECT_MAPPER  = new ObjectHashMapper();
}
