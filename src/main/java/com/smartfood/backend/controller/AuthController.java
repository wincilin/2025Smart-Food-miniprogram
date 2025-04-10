package com.smartfood.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.auth.WxLoginRequestDTO;
import com.smartfood.backend.service.WxAuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @Autowired
    private WxAuthService authService;

    @PostMapping("/login")
    @Operation(summary = "微信登录", description = "使用微信小程序的code进行登录，返回用户信息和token")
    public ResponseEntity<?> login(@RequestBody @Valid WxLoginRequestDTO wxLoginRequestDTO) {
        
        String code = wxLoginRequestDTO.getCode();
        if (code == null) return ResponseEntity.badRequest().body("Missing code");
        //返回的是json格式，此处不封装DTO了
        return ResponseEntity.ok(authService.loginWithWxCode(code));
    }
}
