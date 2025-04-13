package com.smartfood.backend.service;

import org.springframework.stereotype.Service;
import com.smartfood.backend.entity.FoodRecord;
import com.smartfood.backend.repository.FoodRecordRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodRecordService {

    private final FoodRecordRepository foodRecordRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FoodRecord createFoodRecord(String openid, String foodName, 
                                     Double caloriesPer100g, Double weight) {
        FoodRecord record = new FoodRecord();
        record.setOpenid(openid);
        record.setFoodName(foodName);
        record.setCaloriesPer100g(caloriesPer100g);
        record.setWeight(weight);
        record.setRecordTime(LocalDateTime.now().format(TIME_FORMATTER));
        
        // 计算总卡路里
        double totalCalories = (caloriesPer100g * weight) / 100;
        record.setTotalCalories(totalCalories);
        
        return foodRecordRepository.save(record);
    }

    public List<FoodRecord> getUserFoodRecords(String openid) {
        return foodRecordRepository.findByOpenidOrderByRecordTimeDesc(openid);
    }
} 