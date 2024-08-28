package com.fre.nettyserversemo.res;

import io.netty.buffer.ByteBufUtil;

import java.io.Serializable;

public class SubCommand implements Serializable{
    private final static long serialVersionUID=  1L;

    private short type;
    private int length;
    private byte[] data;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SubCommand{" +
                "type=" + type +
                ", length=" + length +
                ", data=" + ByteBufUtil.hexDump(data) +
                '}';
    }
}
