package com.fre.nettyserversemo.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    int length = 0;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("ByteBuf:"+ byteBuf);
        if(byteBuf.readableBytes()>=4){
            if (length == 0){
                length = byteBuf.readInt();
            }
            if(byteBuf.readableBytes()<length) {
                System.out.println("数据量不够");
                return;
            }
            byte[] content = new byte[length];
            byteBuf.readBytes(content);
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLength(length);
            messageProtocol.setContent(content);
            list.add(messageProtocol);
            length = 0;
        }

    }
}
