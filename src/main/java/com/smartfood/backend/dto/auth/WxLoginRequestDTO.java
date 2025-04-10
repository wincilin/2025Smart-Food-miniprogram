package com.smartfood.backend.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginRequestDTO {
    @NotNull(message = "code不能为空")
    String code;
}
