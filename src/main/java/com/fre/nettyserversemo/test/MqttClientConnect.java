package com.fre.nettyserversemo.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;


@Component
public class MqttClientConnect {

    public Channel connectMqtt() throws InterruptedException {
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
                        .clientId("yourClientId2") // 设置你的客户端ID
                        .cleanSession(true)
                        .keepAlive(60)
                        .build();

                // 发送连接消息
                channel.writeAndFlush(connectMessage);

                return channel;

            } finally {
                // 注意：不要在这里关闭EventLoopGroup，因为我们返回的Channel需要它进行数据传输
                // group.shutdownGracefully();

            }
        }
    }
