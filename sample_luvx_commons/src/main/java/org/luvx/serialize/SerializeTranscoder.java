package org.luvx.serialize;

import java.io.Closeable;
import java.io.IOException;

public abstract class SerializeTranscoder {
    /**
     * 序列化
     *
     * @param value 对象
     * @return 结果
     */
    public abstract byte[] serialize(Object value);

    /**
     * 反序列化
     *
     * @param in 字节码
     * @return 对象
     * @throws IOException IO异常
     */
    public abstract Object deserialize(byte[] in) throws IOException;


    /**
     * @param closeable
     */
    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
