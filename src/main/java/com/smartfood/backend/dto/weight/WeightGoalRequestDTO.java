package com.smartfood.backend.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WeightGoalRequestDTO", description = "用户提交的体重目标数据")
// 由于存储数据时采用@AuthenticationPrincipal注入的方式，所以不需要传入用户的openId
public class WeightGoalRequestDTO {
    private Double startWeight;
    private Double currentWeight;
    private Double targetWeight;
}
