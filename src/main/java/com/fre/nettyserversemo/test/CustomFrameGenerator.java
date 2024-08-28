package com.fre.nettyserversemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

@Getter
public class CustomFrameGenerator {
    public static void main(String[] args) {
        float h2s = 1.1f;
        float co = 2.2f;
        float o2 = 3.3f;
        float ch4 = 4.4f;

        CustomFrame frame = new CustomFrame((byte) 0xF0, (byte) 0xA8, h2s, co, o2, ch4);
        byte[] frameBytes = generateFrameBytes(frame);

        System.out.println("Generated Frame Bytes: " + bytesToHex(frameBytes));
    }

    private static byte[] generateFrameBytes(CustomFrame frame) {
        ByteBuf byteBuf = Unpooled.buffer();
        frame.encode(byteBuf);

        // 将 ByteBuf 转换为字节数组
        byte[] frameBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(frameBytes);

        return frameBytes;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X ", b));
        }
        return hexStringBuilder.toString().trim();
    }

}

