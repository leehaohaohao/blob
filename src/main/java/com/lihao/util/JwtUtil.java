package com.lihao.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * @param secretKey 秘钥
     * @param expire 过期时间
     * @param content 内容
     * @return
     */
    public static String createJwt(String secretKey, long expire, Map<String,Object> content) {
        SecureDigestAlgorithm<SecretKey,SecretKey> algorithm = Jwts.SIG.HS256;
        long expireTime = System.currentTimeMillis() + expire;
        Date exp = new Date(expireTime);
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String compact = Jwts.builder()
                .claims(content)
                .expiration(exp)
                .signWith(key, algorithm)
                .compact();
        return compact;
    }

    /**
     * 解析jwt
     * @param secretKey 秘钥
     * @param token token
     * @return
     */
    public static Map<String,Object> parseJwt(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Map<String,Object> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }
}
