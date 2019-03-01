package org.luvx.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * NIO实现的工具类
 */
public class FileUtils {

    /**
     * NIO-读入文件
     *
     * @param path 路径
     * @return 读取为字符串
     * @throws IOException
     */
    public static String readFile(String path) throws IOException {
        StringBuilder result = new StringBuilder();

        @SuppressWarnings("resource")
        FileChannel channel = new FileInputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = -1;
        while ((length = channel.read(buffer)) != -1) {
            buffer.clear();
            byte[] bytes = buffer.array();
            result.append(new String(bytes, 0, length));
        }
        channel.close();
        return result.toString();
    }

    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // 用这个的话，只能输出来一次结果，第二次显示为空
            // charBuffer = decoder.decode(buffer);
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    /**
     * NIO-写入文件
     *
     * @param path    路径
     * @param content 写入内容
     * @return 写入结果
     * @throws IOException
     */
    public static boolean writeFile(String path, String content) throws IOException {
        @SuppressWarnings("resource")
        FileChannel channel = new FileOutputStream(path).getChannel();
        ByteBuffer buffer = Charset.forName("utf8").encode(content);

        while (channel.write(buffer) != 0) {
        }
        channel.close();
        return true;
    }

    /**
     * NIO-文件拷贝
     *
     * @param inputPath
     * @param outputPath
     * @throws IOException
     */
    public static void rw2(String inputPath, String outputPath) throws IOException {
        @SuppressWarnings("resource")
        FileChannel inChannel = new FileInputStream(inputPath).getChannel();
        @SuppressWarnings("resource")
        FileChannel outChannel = new FileOutputStream(outputPath).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Charset charset = Charset.forName("UTF-8");

        CharsetDecoder csDecoder = charset.newDecoder();
        CharsetEncoder csEncoder = charset.newEncoder();

        while (true) {
            buffer.clear();

            CharBuffer cbuffer = csDecoder.decode(buffer);
            ByteBuffer bbuffer = csEncoder.encode(cbuffer);

            int temp = inChannel.read(bbuffer);
            if (temp == -1)
                break;

            bbuffer.flip();
            outChannel.write(bbuffer);
        }
    }
}
