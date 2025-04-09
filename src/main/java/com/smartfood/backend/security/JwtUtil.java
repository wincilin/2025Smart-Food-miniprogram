package com.smartfood.backend.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.smartfood.backend.model.User;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

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
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 从 token 中获取 openid（而不是 userId）
    public String getOpenid(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

