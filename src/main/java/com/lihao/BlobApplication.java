package com.lihao;

import com.lihao.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.lihao.mapper")
@EnableAsync
@EnableScheduling
public class BlobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlobApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(NettyServer nettyServer) {
        return args -> {
            nettyServer.start();
        };
    }
}
