package com.fre.nettyserversemo.res;


import io.netty.buffer.ByteBufUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
@Data
@Getter
@NoArgsConstructor
public class Command implements Serializable{
    private final static long serialVersionUID=  1L;
    private String flag;
    private int length = 0;
    private int cardNo = 0;
    private List<SubCommand> subCommandList;
    private byte[] endFlag;

    public Command(String flag, int length, int cardNo, List<SubCommand> subCommandList, byte[] endFlag) {
        this.flag = flag;
        this.length = length;
        this.cardNo = cardNo;
        this.subCommandList = subCommandList;
        this.endFlag = endFlag;
    }



    @Override
    public String toString() {
        return "Command{" +
                "flag='" + flag + '\'' +
                ", length=" + length +
                ", cardNo=" + cardNo +
                ", subCommandList=" + subCommandList +
                ", endFlag=" + ByteBufUtil.hexDump(endFlag) +
                '}';
    }
}
