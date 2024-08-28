package com.fre.nettyserversemo.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class NettyMessageClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
            String msg = "你好南邮";
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLength(msg.getBytes(StandardCharsets.UTF_8).length);
            messageProtocol.setContent(msg.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {

    }
}
