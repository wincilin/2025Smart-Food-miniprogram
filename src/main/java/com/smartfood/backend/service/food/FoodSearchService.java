package com.smartfood.backend.service.food;

import org.springframework.stereotype.Service;
import com.smartfood.backend.entity.FoodNutrition;
import com.smartfood.backend.repository.food.FoodNutritionRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodSearchService {

    private final FoodNutritionRepository foodNutritionRepository;

    /**
     * 根据关键词搜索食物
     * @param keyword 搜索关键词
     * @return 匹配的食物列表
     */
    public List<FoodNutrition> searchFoodByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return foodNutritionRepository.findAll();
        }
        // 移除所有特殊字符，只保留中文字符
        //String cleanKeyword = keyword.replaceAll("[^\\u4e00-\\u9fa5]", "");
        return foodNutritionRepository.searchByKeyword(keyword);
    }
} 