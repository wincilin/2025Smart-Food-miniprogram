package com.smartfood.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.WeightRequestDTO;
import com.smartfood.backend.dto.WeightResponseDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.service.WeightService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/weight")
// @Tag(name = "WeightController", description = "体重数据相关接口")
public class WeightController {
    @Autowired
    private WeightService weightService;

    @PostMapping
    public ResponseEntity<String> saveWeight(@RequestBody WeightRequestDTO weightDTO, @AuthenticationPrincipal User user) {
        //TODO:weightService.saveWeight(dto, user);
        
        return ResponseEntity.ok("体重数据保存成功");
    }

    @GetMapping
    public ResponseEntity<List<WeightResponseDTO>> getWeight(@AuthenticationPrincipal User user){
        List<WeightResponseDTO> weightList = weightService.getWeights(user);
        return ResponseEntity.ok(weightList);
    }
    
}
