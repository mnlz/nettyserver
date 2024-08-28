package com.fre.nettyserversemo.res;

import com.fre.nettyserversemo.service.RedisService;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@Component
public class CommandHandler extends SimpleChannelInboundHandler<Command> {
    Logger log = LoggerFactory.getLogger(CommandHandler.class);
    @Autowired
    private RedisService redisService;
    public static CommandHandler commandHandler;

    public  CommandHandler () {

    }
    @PostConstruct
    public void init(){
        commandHandler = this;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command msg) {
        Integer expireTime= 30;
        for (SubCommand msgs : msg.getSubCommandList()){

            commandHandler.redisService.hmSet(msg.getFlag(),msg.getFlag(),msgs,expireTime.longValue());
        }

        SubCommand hmget = (SubCommand) commandHandler.redisService.hmget("FMS", "FMS");
        System.out.println("*********************从redis取出的数据********************"+hmget.toString());
        log.info("read msg = " +msg.toString());

    }
}
