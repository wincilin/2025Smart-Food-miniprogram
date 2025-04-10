package com.smartfood.backend.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 这里既用于接收前端传来的数据，也用于返回给前端
 * 由于存储数据时采用@AuthenticationPrincipal注入的方式，所以不需要传入用户的openId
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WeightGoalRequestDTO", description = "用户提交的体重目标数据")
// 由于存储数据时采用@AuthenticationPrincipal注入的方式，所以不需要传入用户的openId
public class WeightGoalRequestDTO {
    @Schema(description = "体重初始值", example = "60.0")
    private Double startWeight;

    @Schema(description = "体重当前值", example = "65.0")
    private Double currentWeight;

    @Schema(description = "体重目标值", example = "55.0")
    private Double targetWeight;
}
