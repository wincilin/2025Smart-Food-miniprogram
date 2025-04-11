package com.smartfood.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.dto.weight.WeightGoalRequestDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.security.LoginUser;
import com.smartfood.backend.service.WeightGoalService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/weight/goal")
public class WeightGoalController {
    @Autowired
    private WeightGoalService weightGoalService;

    @PostMapping("/save")
    @Operation(summary = "保存体重目标", description = "保存当前登录用户的体重初始/当前/目标数据")
    public ResponseEntity<?> saveWeightGoal(@RequestBody @Valid WeightGoalRequestDTO weightGoalDTO, @AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        weightGoalService.saveWeightGoal(weightGoalDTO, user);
        return ResponseEntity.ok(new ApiResponse<>(true,"Successfully save weight goal", null));
    }

    @GetMapping("/getGoal")
    @Operation(summary = "获取体重目标", description = "获取当前登录用户的体重初始/当前/目标数据")
    public ResponseEntity<WeightGoalRequestDTO> getWeightGoal(@AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        WeightGoalRequestDTO weightGoal = weightGoalService.getWeightGoal(user);
        return ResponseEntity.ok(weightGoal);
    }
    
}
