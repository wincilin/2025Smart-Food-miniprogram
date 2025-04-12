package com.smartfood.backend.dto.user;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserInfoDTO", description = "用户信息数据传输对象")
public class UserInfoDTO {
    private String userName;
    private LocalDate birthdate;
    private String gender;
    private String height; // 身高
}
