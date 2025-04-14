package com.smartfood.backend.dto.food;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecordSaveDTO {
    @NotNull(message = "食物名称不能为空")
    String foodName;

    @NotNull(message = "每100克卡路里不能为空")
    Double caloriesPer100g;
    
    @NotNull(message = "进食重量不能为空")
    Double weight;
}
