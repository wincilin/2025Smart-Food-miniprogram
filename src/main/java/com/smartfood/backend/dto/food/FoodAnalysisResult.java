package com.smartfood.backend.dto.food;

import lombok.Data;
import java.util.List;

@Data
public class FoodAnalysisResult {
    private List<FoodItem> result;
    private String logId;
    private int resultNum;

    @Data
    public static class FoodItem {
        private String name;
        private String calorie;
        private boolean hasCalorie;
        private double probability;
    }
} 