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
import com.smartfood.backend.service.WeightGoalService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/weight/goal")
public class WeightGoalController {
    @Autowired
    private WeightGoalService weightGoalService;

    @PostMapping("/save")
    public ResponseEntity<?> saveWeightGoal(@RequestBody @Valid WeightGoalRequestDTO weightGoalDTO, @AuthenticationPrincipal User user) {
        weightGoalService.saveWeightGoal(weightGoalDTO, user);
        return ResponseEntity.ok(new ApiResponse<>(true,"Successfully save weight goal", null));
    }

    @GetMapping("/getGoal")
    public ResponseEntity<WeightGoalRequestDTO> getWeightGoal(@AuthenticationPrincipal User user) {
        WeightGoalRequestDTO weightGoal = weightGoalService.getWeightGoal(user);
        return ResponseEntity.ok(weightGoal);
    }
    
}
