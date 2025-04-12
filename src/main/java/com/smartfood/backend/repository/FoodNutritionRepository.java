package com.smartfood.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.smartfood.backend.entity.FoodNutrition;
import java.util.List;

@Repository
public interface FoodNutritionRepository extends JpaRepository<FoodNutrition, Long> {
    
    // 使用LIKE进行模糊搜索
    @Query("SELECT f FROM FoodNutrition f WHERE f.name LIKE %:keyword%")
    List<FoodNutrition> searchByKeyword(@Param("keyword") String keyword);
} 