package com.smartfood.backend.dto.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodTodayTotalCaloriesDTO {
    
    private Double totalCalories; // 今日总卡路里
}
