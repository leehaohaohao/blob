package com.lihao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.lihao.mapper")
public class BlobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlobApplication.class, args);
    }

}
