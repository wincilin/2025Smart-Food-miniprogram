package com.smartfood.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WxLoginRequestDTO {
    @NotNull(message = "code不能为空")
    String code;
}
