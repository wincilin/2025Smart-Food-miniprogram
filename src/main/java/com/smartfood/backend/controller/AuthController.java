package com.smartfood.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.auth.WxLoginRequestDTO;
import com.smartfood.backend.service.WxAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @Autowired
    private WxAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid WxLoginRequestDTO wxLoginRequestDTO) {
        
        String code = wxLoginRequestDTO.getCode();
        if (code == null) return ResponseEntity.badRequest().body("Missing code");
        //返回的是json格式，此处不封装DTO了
        return ResponseEntity.ok(authService.loginWithWxCode(code));
    }
}
