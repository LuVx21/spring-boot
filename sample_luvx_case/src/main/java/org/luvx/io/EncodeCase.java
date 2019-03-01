package org.luvx.io;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 确认不同编码方式存储的差别
 */
public class EncodeCase {

    private static void encode(String s, Charset charset) {
        byte[] byte1 = charset == null ? s.getBytes() : s.getBytes(charset);
        for (byte b : byte1) {
            System.out.print(Integer.toHexString(b & 0xff) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        encode("foobar", StandardCharsets.UTF_16BE);
        encode("foo你好こんにちはbar", StandardCharsets.UTF_16BE);
    }
}
