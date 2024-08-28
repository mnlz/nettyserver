package com.fre.nettyserversemo;

import com.fre.nettyserversemo.res.FreServer;
import com.fre.nettyserversemo.test.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class NettyserversemoApplication implements CommandLineRunner {
	@Resource
	private FreServer freServer;
	@Resource
	private NettyServer nettyServer;

	public static void main(String[] args) {
		SpringApplication.run(NettyserversemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// freServer.run(7788);
		nettyServer.run(7788);
	}
}
