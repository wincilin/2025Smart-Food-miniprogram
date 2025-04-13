package com.smartfood.backend.controller.food;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.smartfood.backend.dto.food.FoodRecordGetDTO;
import com.smartfood.backend.entity.FoodRecord;
import com.smartfood.backend.security.LoginUser;
import com.smartfood.backend.service.FoodRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/food/record")
@RequiredArgsConstructor
public class FoodRecordController {

    private final FoodRecordService foodRecordService;

    @PostMapping
    @Operation(summary = "创建食物记录", description = "创建新的食物记录")
    public ResponseEntity<String> createFoodRecord(@RequestBody FoodRecord foodRecord, @AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        foodRecordService.createFoodRecord(openid, foodRecord.getFoodName(), foodRecord.getCaloriesPer100g(), foodRecord.getWeight());
        return ResponseEntity.ok("食物记录创建成功");
    }

    @GetMapping("/user")
    public ResponseEntity<List<FoodRecordGetDTO>> getUserFoodRecords(@AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        List<FoodRecordGetDTO> foodRecords = foodRecordService.getUserFoodRecords(openid);
        return ResponseEntity.ok(foodRecords);
    }
} 