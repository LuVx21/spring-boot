package org.luvx.boot.redis.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * increment 有问题
 * redis 客户端不支持查看, protobuf支持查看
 */
public final class ProtostuffRedisSerializer implements RedisSerializer<Object> {

    final Schema<CacheValueWrapper> schema = RuntimeSchema.getSchema(CacheValueWrapper.class);

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        final LinkedBuffer buffer = LinkedBuffer.allocate();
        try {
            return ProtostuffIOUtil.toByteArray(new CacheValueWrapper(o), schema, buffer);
        } catch (RuntimeException e) {
            throw new SerializationException(e.getMessage());
        } finally {
            buffer.clear();
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        CacheValueWrapper wrapper = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, wrapper, schema);
        return wrapper.getData();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheValueWrapper {
        private Object data;
    }
}

