package com.fre.nettyserversemo.res;

public enum FreDecoderState {
    READ_FLAG,
    READ_LENGTH,
    READ_CARDNO,
    READ_CONTENT,
    READ_END,
}
