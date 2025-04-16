package com.smartfood.backend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.smartfood.backend.entity.User;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 通过 User 实体生成 token
    public String generateToken(User user) {
        return generateTokenByOpenid(user.getOpenid());
    }

    // 通过 openid 生成 token（用于 mock 或首次登录时只有 openid）
    public String generateTokenByOpenid(String openid) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(openid)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 新版 API：需要 Key + 算法
                .compact();
    }

    // 从 token 中获取 openid（而不是 userId）
    public String getOpenid(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 新版 API：使用 parserBuilder
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
