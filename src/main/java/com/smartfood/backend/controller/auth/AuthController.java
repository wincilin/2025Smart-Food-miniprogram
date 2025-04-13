package com.smartfood.backend.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.auth.WxLoginResponseDTO;
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
    public ResponseEntity<WxLoginResponseDTO> login(@RequestBody @Valid WxLoginRequestDTO wxLoginRequestDTO) {
        
        String code = wxLoginRequestDTO.getCode();
        if (code == null) {
            return ResponseEntity.badRequest().body(new WxLoginResponseDTO(null, "Missing code", false));
        }
        
        WxLoginResponseDTO wxAuthResponseDTO = authService.loginWithWxCode(code);
        return ResponseEntity.ok(wxAuthResponseDTO);
    }
}
