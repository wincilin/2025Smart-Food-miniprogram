package com.smartfood.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartfood.backend.entity.FoodNutrition;

@Repository
public interface FoodNutritionRepository extends JpaRepository<FoodNutrition, Long> {
    // 基础的 CRUD 操作由 JpaRepository 提供
} 