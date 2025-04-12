package com.smartfood.backend.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginResponseDTO {
    
    @NotNull
    String token;

    @NotNull
    String openid;

    @NotNull
    boolean if_first_login = false; // 是否第一次登录
}
