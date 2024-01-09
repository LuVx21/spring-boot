package org.luvx.boot.redis.other;

import jakarta.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
    public static byte[] serialize(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("序列化异常");
        }
    }

    @Nullable
    public static Object deserialize(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                return ois.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("反序列化异常");
        }
    }
}
