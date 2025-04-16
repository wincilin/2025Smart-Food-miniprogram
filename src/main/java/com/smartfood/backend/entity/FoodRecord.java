package com.smartfood.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FoodRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String openid;  // 用户openid

    @Column(nullable = false)
    private String foodName;  // 食物名称

    @Column(nullable = false)
    private Double caloriesPer100g;  // 每100克卡路里（千卡）

    @Column(nullable = false)
    private Double weight;  // 进食重量（克）

    @Column(nullable = false)
    private String recordTime;  // 记录时间（格式：yyyy-MM-dd HH:mm）

    @Column(nullable = false)
    private Double totalCalories;  // 总卡路里 = (caloriesPer100g * weight) / 100
} 