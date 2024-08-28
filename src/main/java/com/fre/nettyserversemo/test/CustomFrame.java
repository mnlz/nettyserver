package com.fre.nettyserversemo.test;

import io.netty.buffer.ByteBuf;


public class CustomFrame {
    private static final short HEADER = (short) 0xE7CC;
    private static final short FOOTER = 0x01ED;

    private byte frameType;
    private byte replyType;
    private short frameLength;
    private float h2s;
    private float co;
    private float o2;
    private float ch4;

    public CustomFrame(byte frameType, byte replyType, float h2s, float co, float o2, float ch4) {
        this.frameType = frameType;
        this.replyType = replyType;
        this.frameLength = 16; // 四个float类型，每个占4字节，共16字节
        this.h2s = h2s;
        this.co = co;
        this.o2 = o2;
        this.ch4 = ch4;
    }
    public void encode(ByteBuf byteBuf) {
        byteBuf.writeShort(HEADER);  // 帧头
        byteBuf.writeByte(frameType);
        byteBuf.writeByte(replyType);
        byteBuf.writeShort(frameLength);
        byteBuf.writeFloat(h2s);
        byteBuf.writeFloat(co);
        byteBuf.writeFloat(o2);
        byteBuf.writeFloat(ch4);
        byteBuf.writeShort(FOOTER);  // 帧尾
    }

    @Override
    public String toString() {
        return "CustomFrame{" +
                "frameType=" + frameType +
                ", replyType=" + replyType +
                ", frameLength=" + frameLength +
                ", h2s=" + h2s +
                ", co=" + co +
                ", o2=" + o2 +
                ", ch4=" + ch4 +
                '}';
    }
}


