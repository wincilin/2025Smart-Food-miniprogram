package com.smartfood.backend.dto.weight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


//由于存储数据时采用@AuthenticationPrincipal注入的方式，所以不需要传入用户的openId
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WeightRequestDTO", description = "用户提交的体重数据")
public class WeightRequestDTO {
    @NotNull
    @Schema(description = "体重值")
    Double weight;

    @NotNull
    @Schema(description = "日期（格式为 yyyy-MM-dd）")
    String date;
}
