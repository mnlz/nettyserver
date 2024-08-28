package com.fre.nettyserversemo.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MessageDecode extends ByteToMessageDecoder {
    private static final short HEADER = (short) 0xE7CC;
    private static final short FOOTER = 0x01ED;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        // 确保至少有帧头和帧尾的长度
        if (in.readableBytes() < 6) {
            return;
        }

        // 校验帧头
        short header = in.readShort();
        log.info("header: " + header);
        if (header != HEADER) {
            in.clear(); // 丢弃不符合格式的数据
            return;
        }

        // 读取帧类型、回复类型和帧长度
        byte frameType = in.readByte();
        byte replyType = in.readByte();
        int frameLength = in.readShort();

        // 检查是否有足够的字节用于读取整个帧
        if (in.readableBytes() < frameLength + 2) {  // 加上帧尾的长度
            in.resetReaderIndex(); // 重置读取位置
            return;
        }

        // 读取四个float类型的小数
        float h2s = in.readFloat();
        float co = in.readFloat();
        float o2 = in.readFloat();
        float ch4 = in.readFloat();

        // 校验帧尾
        short footer = in.readShort();
        if (footer != FOOTER) {
            in.clear(); // 丢弃不符合格式的数据
            return;
        }
        // TODO 气体校验

        // 创建并输出解析后的帧对象
        CustomFrame frame = new CustomFrame(frameType, replyType, h2s, co, o2, ch4);
        out.add(frame);
    }
}
