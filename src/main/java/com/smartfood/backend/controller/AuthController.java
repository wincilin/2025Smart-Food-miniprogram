package com.smartfood.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.service.WxAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @Autowired
    private WxAuthService authService;
    // @Autowired
    // private UserService userService;
    // @Autowired
    // private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        
        String code = body.get("code");
        if (code == null) return ResponseEntity.badRequest().body("Missing code");
        return ResponseEntity.ok(authService.loginWithWxCode(code));
    }
}
