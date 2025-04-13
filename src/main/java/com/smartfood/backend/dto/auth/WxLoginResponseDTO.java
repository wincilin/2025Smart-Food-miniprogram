package com.smartfood.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "微信登录返回信息")
public class WxLoginResponseDTO {
    
    @NotNull
    String token;

    @NotNull
    String openid;

    @NotNull
    @Schema(description = "是否为第一次登录，true代表第一次")
    boolean if_first_login = false; // 是否第一次登录
}
