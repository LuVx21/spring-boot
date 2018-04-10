package org.luvx.api.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道 -> 缓冲区 -> 通道
 */

public class NIO {

    /**
     * 通道 缓冲区数据流向细节分析
     *
     * @throws IOException
     */
    @Test
    public void NIOTest() throws IOException {
        String path = "/Users/renxie/code/luvx_trial/sample_luvx_ssm/1.log";

        FileChannel channel = new FileInputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);

        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());
        // 从通道中读取数据进入缓冲区
        System.out.println(channel.read(buffer));
        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());

        System.out.println(channel.read(buffer));
        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());

        System.out.println("---------");
        // 在读取缓冲区前使用,将limit设置为现在的position,position设置为0
        buffer.flip();
        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());
        for (int i = 0; i < 4; i++) {
            // 从缓冲区读取数据进入通道,读取的是position到limit之间的数据
            buffer.get();
        }
        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());

        System.out.println("---------");
        // 返回初始状态
        buffer.clear();
        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());
        System.out.println(channel.read(buffer));
    }
}
