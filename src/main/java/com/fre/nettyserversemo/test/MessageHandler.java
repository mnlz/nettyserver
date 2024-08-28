package com.fre.nettyserversemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;

public class MessageHandler extends SimpleChannelInboundHandler<CustomFrame> {

    private final String mqttBrokerHost = "47.118.36.78";
    private final int mqttBrokerPort = 1883; // 注意：这里使用的是MQTT默认端口，而不是之前的WebSocket端口
    private Channel mqttChannel;
    public MessageHandler(Channel mqttChannel) {
        this.mqttChannel = mqttChannel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomFrame customFrame) throws Exception {
        System.out.println(customFrame.toString());
        // 将 CustomFrame 数据转换为 ByteBuf，这里需要根据实际情况进行编码
        ByteBuf payload = Unpooled.buffer();

        customFrame.encode(payload);

        // 构建并发送你的MQTT消息
        // 例如，发布一个消息到名为 "test/topic" 的主题
        MqttPublishMessage message = MqttMessageBuilders.publish()
                .topicName("abc")
                .retained(false)
                .qos(MqttQoS.AT_LEAST_ONCE)
                .payload(payload)
                .build();

        mqttChannel.writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




}
