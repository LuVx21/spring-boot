package org.luvx.utils;

import com.alibaba.fastjson.JSON;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializeUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    /**
     * jdk 序列化工具的序列化与反序列化
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] JDKObjectToBytes(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);
        objectOutput.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> T JDKBytesToObject(byte[] bytes, Class clazz) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Object object = objectInputStream.readObject();
        return (T) object;
    }

    /**
     * 使用fastjson实现序列化和反序列化
     *
     * @param object
     * @return
     */
    public static byte[] FastJsonObjectToBytes(Object object) {
        byte[] bytes = JSON.toJSONBytes(object);
        return bytes;
    }

    /**
     * fastjosn反序列化时，class必须要有默认构造函数，否则报错
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T FastJsonBytesToObject(byte[] bytes, Class clazz) {
        return (T) JSON.parseObject(bytes, clazz);
    }

    /**
     * 使用protostuff序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 缓存schema
     *
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }
}