package com.smartfood.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartfood.backend.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll() //放行登录接口
                .requestMatchers("/api/food/count").permitAll() //放行食物数量接口
                .requestMatchers("/api/food/list").permitAll() //放行食物列表接口
                .requestMatchers("/api/food/search").permitAll() //放行食物搜索接口
                .requestMatchers("/api/food/record").permitAll() //放行食物记录接口
                .requestMatchers("/api/food/record/user/**").permitAll() //放行用户食物记录查询接口
                .requestMatchers("/api/photo").permitAll() //放行照片分析接口
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll() // 放行 Swagger 相关接口
                .anyRequest().authenticated()             // 其他接口需要 token
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 加入你的 JWT 过滤器

        return http.build();
    }
}