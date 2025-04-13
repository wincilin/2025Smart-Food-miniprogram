package com.smartfood.backend.dto.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecordGetDTO {

    private Long id;

    private String foodName;  // 食物名称

    private Double caloriesPer100g;  // 每100克卡路里（千卡）

    private Double weight;  // 进食重量（克）

    private String recordTime;  // 记录时间（格式：yyyy-MM-dd HH:mm）

    private Double totalCalories;  // 总卡路里 = (caloriesPer100g * weight) / 100
} 