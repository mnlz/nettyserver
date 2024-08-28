package com.fre.nettyserversemo.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageProtocol {

    //消息的长度
    private int length;

    //消息的内容
    private byte[] content;
}
