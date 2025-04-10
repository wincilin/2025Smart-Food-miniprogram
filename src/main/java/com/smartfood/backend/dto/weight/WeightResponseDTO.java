package com.smartfood.backend.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "WeightResponseDTO", description = "返回给前端的单条体重记录")
public class WeightResponseDTO {
    @Schema(description = "记录 ID")
    Long id;

    @Schema(description = "体重")
    Double weight;

    @Schema(description = "日期（格式为 yyyy-MM-dd）")
    String date;

    @Schema(description = "用户 openId")
    String openId;
}
