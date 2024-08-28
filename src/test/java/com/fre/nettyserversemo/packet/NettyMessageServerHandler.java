package com.fre.nettyserversemo.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class NettyMessageServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        System.out.println("-------服务器收到的消息--------------");
        System.out.println("消息的长度："+messageProtocol.getLength());
        System.out.println("消息的内容" + new String(messageProtocol.getContent(), StandardCharsets.UTF_8) );
    }
}
