package com.smartfood.backend.controller.weight;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.weight.WeightRequestDTO;
import com.smartfood.backend.dto.weight.WeightResponseDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.security.LoginUser;
import com.smartfood.backend.service.weight.WeightService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/weight")
// @Tag(name = "WeightController", description = "体重数据相关接口")
public class WeightController {
    @Autowired
    private WeightService weightService;

    @PostMapping("/save")
    @Operation(summary = "保存体重数据", description = "保存当前登录用户的单次体重数据")
    public ResponseEntity<String> saveWeight(@RequestBody WeightRequestDTO weightDTO, @AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        weightService.saveWeight(weightDTO, user);
        
        return ResponseEntity.ok("体重数据保存成功");
    }

    @GetMapping("/getData")
    @Operation(summary = "获取体重数据", description = "获取当前登录用户的所有体重数据")
    public ResponseEntity<List<WeightResponseDTO>> getWeight(@AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        List<WeightResponseDTO> weightList = weightService.getWeights(user);
        return ResponseEntity.ok(weightList);
    }
    
}
