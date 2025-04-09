package com.smartfood.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 关闭 csrf（前后端分离推荐）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll() // ✅ 放行登录接口
                .anyRequest().authenticated() // 其他接口需要认证
            )
            .httpBasic(); // 控制是否出现“登录框”，可以不加

        return http.build();
    }
}
