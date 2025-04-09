package com.smartfood.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 关闭 csrf（前后端分离推荐）
            .authorizeHttpRequests(auth -> auth
                // .requestMatchers("/api/auth/login").permitAll() // ✅ 放行登录接口
                // .anyRequest().authenticated() // 其他接口需要认证
                .anyRequest().permitAll() 
            );
        return http.build();
    }
}
