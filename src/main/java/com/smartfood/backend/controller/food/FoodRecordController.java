package com.smartfood.backend.controller.food;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.smartfood.backend.dto.food.FoodRecordGetDTO;
import com.smartfood.backend.dto.food.FoodRecordSaveDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.security.LoginUser;
import com.smartfood.backend.service.food.FoodRecordService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/food/record")
@RequiredArgsConstructor
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    @PostMapping
    @Operation(summary = "创建食物记录", description = "创建新的食物记录")
    public ResponseEntity<String> createFoodRecord(@RequestBody @Valid FoodRecordSaveDTO foodRecordSaveDTO, @AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        foodRecordService.createFoodRecord(openid, foodRecordSaveDTO.getFoodName(), foodRecordSaveDTO.getCaloriesPer100g(), foodRecordSaveDTO.getWeight());
        return ResponseEntity.ok("食物记录创建成功");
    }

    @GetMapping("/user")
    public ResponseEntity<List<FoodRecordGetDTO>> getUserFoodRecords(@AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        List<FoodRecordGetDTO> foodRecords = foodRecordService.getUserFoodRecords(openid);
        return ResponseEntity.ok(foodRecords);
    }

    @GetMapping("/today")
    public ResponseEntity<Double> getTodaySumCalories(@AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        String today = LocalDate.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Double todayCalories = foodRecordService.getCertainDayCumCalories(user.getOpenid(), today);
        return ResponseEntity.ok(todayCalories);
    }
    
} 