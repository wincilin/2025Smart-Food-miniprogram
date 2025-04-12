package com.smartfood.backend.dto.user;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateUserProfileDTO", description = "用户更新个人信息的请求数据")
public class UpdateUserProfileDTO {
    private String userName;
    private LocalDate birthdate;
    private String gender;
    private String height; // 身高
}
