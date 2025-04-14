package com.smartfood.backend.service;

import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.food.FoodRecordGetDTO;
import com.smartfood.backend.entity.FoodRecord;
import com.smartfood.backend.repository.FoodRecordRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodRecordService {

    private final FoodRecordRepository foodRecordRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void createFoodRecord(String openid, String foodName, 
                                     Double caloriesPer100g, Double weight) {
        FoodRecord record = new FoodRecord();
        record.setOpenid(openid);
        record.setFoodName(foodName);
        record.setCaloriesPer100g(caloriesPer100g);
        record.setWeight(weight);
        record.setRecordTime(
            LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(TIME_FORMATTER)
        );
        System.out.println(openid + " " + foodName + " " + caloriesPer100g + " " + weight + " " + record.getRecordTime());
        // 计算总卡路里
        double totalCalories = (caloriesPer100g * weight) / 100;
        record.setTotalCalories(totalCalories);
        
        foodRecordRepository.save(record);
    }

    public List<FoodRecordGetDTO> getUserFoodRecords(String openid) {
        List<FoodRecord> foodRecords = foodRecordRepository.findByOpenidOrderByRecordTimeDesc(openid);
        List<FoodRecordGetDTO> foodRecordGetDTOs = foodRecords.stream()
                .map(record -> new FoodRecordGetDTO(
                        record.getId(),
                        record.getFoodName(),
                        record.getCaloriesPer100g(),
                        record.getWeight(),
                        record.getRecordTime(),
                        record.getTotalCalories()))
                .toList();
        return foodRecordGetDTOs;
    }
} 