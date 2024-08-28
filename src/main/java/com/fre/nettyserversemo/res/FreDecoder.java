package com.fre.nettyserversemo.res;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fre.nettyserversemo.res.FreDecoderState.*;

//46 4d 53 00 0e FF FF 82 00 07 a1 10 e0 a1 23 a5

@Slf4j
@Component
public class FreDecoder extends ReplayingDecoder<FreDecoderState> {

    public FreDecoder() {
        super(FreDecoderState.READ_FLAG);
    }

    final static int BEGIN_FLAG_LENGTH = 3;
    final static int FLEX_HEAD_LENGTH = 7;
    final static int FLEX_SUB_HEAD_LENGTH = 3;
    final static int END_FLAG_LENGTH = 2;

    private String flag;
    private int length = 0;
    private int cardNo = 0;
    private ByteBuf dataBuf = Unpooled.directBuffer(512);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        switch (state()) {
            case READ_FLAG:
                // 它从输入的字节缓冲区中读取指定长度（BEGIN_FLAG_LENGTH）的字符序列，并使用指定的字符集（UTF-8）进行解码
                // 基于当前 ByteBuf 的读取索引（read index）进行的
                // 读取的是46 4d 53
                flag = in.readCharSequence(BEGIN_FLAG_LENGTH, StandardCharsets.UTF_8).toString();
                log.info("flag: " + flag);
                checkpoint(READ_LENGTH);
                break;

            case READ_LENGTH:
                // 读取 00 0e readUnsignedShort读取的两个字节的数据 十进制为14
                // 读取两个16位的数据，或者也是两个字节的数据 FF一共是8位二进制
                length = in.readUnsignedShort();
                log.info("length: " + length);
                checkpoint(READ_CARDNO);
                break;

            case READ_CARDNO:
                // 读取卡号 FF FF 需要读取两个字节
                cardNo = in.readUnsignedShort();
                log.info("cardNo: " + cardNo);
                checkpoint(READ_CONTENT);
                break;

            case READ_CONTENT:
                // 14 - 7 开始标识+长度+卡号 = 3+2+2 读取数据的长度
                byte[] tempBytes = new byte[length - FLEX_HEAD_LENGTH];

                in.readBytes(tempBytes);
                dataBuf.clear();
                dataBuf.writeBytes(tempBytes);
                // 82 00 07 a1 10 e0 a1
                log.info("tempBytes: " + Arrays.toString(tempBytes));
                checkpoint(READ_END);
                break;


            case READ_END:
                // 读取最后的两位 23 a5
                byte[] endFlag = new byte[END_FLAG_LENGTH];
                in.readBytes(endFlag);
                checkpoint(READ_FLAG);

                List<SubCommand> subCommands = new ArrayList<>();
                while (dataBuf.isReadable()) {
                    SubCommand subCommand = new SubCommand();
                    //读取子文件类型
                    short type = dataBuf.readUnsignedByte();
                    //读取子文件长度
                    int length = dataBuf.readUnsignedShort();
                    //减去文件头部3字节
                    int dataLength = length - FLEX_SUB_HEAD_LENGTH;

                    //读取子文件数据
                    byte[] data = new byte[dataLength];
                    dataBuf.readBytes(data);

                    subCommand.setType(type);
                    subCommand.setLength(length);
                    subCommand.setData(data);
                    subCommands.add(subCommand);
                }

                //封装一个完整的消息
                Command command = new Command(flag, length, cardNo, subCommands, endFlag);
                out.add(command);

                this.clearResource();
                break;
            default:
                ctx.channel().close();
                throw new Error("Shouldn't reach here.");
        }
    }

    private void clearResource() {
        this.flag = null;
        this.length = 0;
        this.cardNo = 0;
        this.dataBuf.clear();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.clearResource();
        super.channelInactive(ctx);
    }
}
