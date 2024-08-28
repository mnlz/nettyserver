package com.fre.nettyserversemo.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.*;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttQoS;

public class MqttClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(MqttEncoder.INSTANCE);
                            // 添加更多处理器，如MQTT消息解码器，业务处理器等
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 1883).sync(); // 替换为你的MQTT服务器地址和端口
            Channel channel = future.channel();

            // 构建连接消息
            MqttConnectMessage connectMessage = MqttMessageBuilders.connect()
                    .protocolVersion(MqttVersion.MQTT_3_1_1)
                    .clientId("yourClientId") // 设置你的客户端ID
                    .cleanSession(true)
                    .keepAlive(60)
                    .build();

            // 发送连接消息
            channel.writeAndFlush(connectMessage);

            // 构建并发送你的MQTT消息
            // 例如，发布一个消息到名为 "test/topic" 的主题
            MqttPublishMessage message = MqttMessageBuilders.publish()
                    .topicName("abc")
                    .retained(false)
                    .qos(MqttQoS.AT_LEAST_ONCE)
                    .payload(Unpooled.wrappedBuffer("hello world".getBytes()))
                    .build();

            channel.writeAndFlush(message);

            // 等待直到连接关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
