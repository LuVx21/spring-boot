package org.luvx.boot.redis.serializer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 和jackson基本一致, 没发现问题
 */
@Slf4j
@RequiredArgsConstructor
public class FastJson2RedisSerializer<T> implements RedisSerializer<T> {

    static final Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter(
            // 按需加上需要支持自动类型的类名前缀，范围越小越安全
            "com.***.***"
    );

    private final Class<T> clazz;

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(t, JSONWriter.Feature.WriteClassName);
        } catch (Exception e) {
            throw new SerializationException("无法序列化: " + e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        try {
            return JSON.parseObject(bytes, clazz, AUTO_TYPE_FILTER);
        } catch (Exception e) {
            throw new SerializationException("无法反序列化: " + e.getMessage(), e);
        }
    }
}
