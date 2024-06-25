package org.luvx.boot.redis.serializer;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import org.apache.commons.lang3.ArrayUtils;
import org.luvx.boot.redis.protobuf.ProtoWrapperProto.ProtoWrapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import jakarta.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * 局限性较多(不能自增,不支持非proto的类型): 关键原因就是要求序列化的对象级联proto
 * 适合grpc取得数据后放入redis的场景
 * <p>
 * 使用了wrapper, 可以不使用泛型(直接指定为GeneratedMessage)
 */
public class ProtobufRedisSerializer implements RedisSerializer<GeneratedMessage> {
    @Override
    public byte[] serialize(GeneratedMessage t) throws SerializationException {
        if (t == null) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }

        try {
            ProtoWrapper wrapper = ProtoWrapper.newBuilder()
                    .setClass_(t.getClass().getName())
                    .setData(t.toByteString())
                    .build();
            return wrapper.toByteArray();
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

    @Override
    @Nullable
    public GeneratedMessage deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        try {
            ProtoWrapper wrapper = ProtoWrapper.parseFrom(bytes);
            ByteString data = wrapper.getData();
            Class<?> clazz = Class.forName(wrapper.getClass_());

            Method method = clazz.getMethod("parseFrom", ByteString.class);
            return (GeneratedMessage) method.invoke(clazz, data);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }
}