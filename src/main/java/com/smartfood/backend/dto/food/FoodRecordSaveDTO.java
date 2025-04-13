package com.smartfood.backend.dto.food;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecordSaveDTO {
    String foodName;

    Double caloriesPer100g;
    
    Double weight;
}
