package com.smartfood.backend.controller;

import org.springframework.web.bind.annotation.*;
import com.smartfood.backend.entity.FoodRecord;
import com.smartfood.backend.service.FoodRecordService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/food/record")
@RequiredArgsConstructor
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    @PostMapping
    public FoodRecord createFoodRecord(
            @RequestParam String openid,
            @RequestParam String foodName,
            @RequestParam Double caloriesPer100g,
            @RequestParam Double weight) {
        return foodRecordService.createFoodRecord(openid, foodName, caloriesPer100g, weight);
    }

    @GetMapping("/user/{openid}")
    public List<FoodRecord> getUserFoodRecords(@PathVariable String openid) {
        return foodRecordService.getUserFoodRecords(openid);
    }
} 