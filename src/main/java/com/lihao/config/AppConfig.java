package com.lihao.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfig {
    @Value("${ws.port}")
    private Integer wsPort;
    @Value("${ws.heart}")
    private Integer wsHeart;
    @Value("${ws.expire}")
    private long expireTime;
}
