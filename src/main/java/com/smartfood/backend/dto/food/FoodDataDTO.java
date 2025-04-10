package com.smartfood.backend.dto.food;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDataDTO {
    @NotNull(message = "食物名字不能为空")
    String foodName;

    @NotNull(message = "食物热量不能为空")
    Double foodCalorie;

    String description;
    //TODO:食物其他的属性？例如图片之类的
}
