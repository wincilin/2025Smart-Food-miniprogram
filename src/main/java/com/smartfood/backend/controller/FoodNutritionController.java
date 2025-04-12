package com.smartfood.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smartfood.backend.entity.FoodNutrition;
import com.smartfood.backend.repository.FoodNutritionRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodNutritionController {

    private final FoodNutritionRepository foodNutritionRepository;

    @GetMapping("/count")
    public long getTotalCount() {
        return foodNutritionRepository.count();
    }

    @GetMapping("/list")
    public List<FoodNutrition> listAll() {
        return foodNutritionRepository.findAll();
    }
} 