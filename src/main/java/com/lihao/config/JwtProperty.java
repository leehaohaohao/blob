package com.lihao.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtProperty {
    @Value("${jwt.secret}")
    public String JWT_SECRET;
    @Value("${jwt.expire}")
    public Long JWT_EXPIRE;
    @Value("${jwt.token}")
    public String JWT_TOKEN;
}
