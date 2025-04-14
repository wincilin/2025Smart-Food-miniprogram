package com.smartfood.backend.controller.food;

import org.springframework.web.bind.annotation.*;
import com.smartfood.backend.entity.FoodNutrition;
import com.smartfood.backend.service.food.FoodSearchService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/food/search")
@RequiredArgsConstructor
public class FoodSearchController {

    private final FoodSearchService foodSearchService;

    @GetMapping
    public List<FoodNutrition> searchFood(@RequestParam(required = false) String keyword) {
        return foodSearchService.searchFoodByKeyword(keyword);
    }
}