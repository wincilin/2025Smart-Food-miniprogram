package com.smartfood.backend.security;

import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 获取 Authorization 头
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // 去掉 "Bearer "

            try {
                // 2. 从 token 提取 openid
                String openid = jwtUtil.getOpenid(token);

                // 3. 如果上下文中还没有认证信息，就查库并注入
                if (openid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    userRepository.findByOpenid(openid).ifPresent(user -> {
                        LoginUser loginUser = new LoginUser(user);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }

            } catch (Exception e) {
                // token 无效：不设置登录状态，继续后续处理（比如交给 Spring Security 拦截）
                logger.warn("JWT token 校验失败: " + e.getMessage());
            }
        }

        // 4. 放行请求
        filterChain.doFilter(request, response);
    }
}

