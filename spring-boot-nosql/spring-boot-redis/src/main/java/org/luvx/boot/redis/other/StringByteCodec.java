package org.luvx.boot.redis.other;

import io.lettuce.core.codec.RedisCodec;
import org.luvx.coding.common.util.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_BYTE_ARRAY;

public class StringByteCodec implements RedisCodec<String, byte[]> {

    public static final StringByteCodec INSTANCE = new StringByteCodec();

    private static final Charset charset = StandardCharsets.UTF_8;

    @Override
    public String decodeKey(final ByteBuffer bytes) {
        return charset.decode(bytes).toString();
    }

    @Override
    public byte[] decodeValue(final ByteBuffer bytes) {
        return BufferUtils.getBytes(bytes);
    }

    @Override
    public ByteBuffer encodeKey(final String key) {
        return charset.encode(key);
    }

    @Override
    public ByteBuffer encodeValue(final byte[] value) {
        byte[] bytes = value == null ? EMPTY_BYTE_ARRAY : value;
        return ByteBuffer.wrap(bytes);
    }
}