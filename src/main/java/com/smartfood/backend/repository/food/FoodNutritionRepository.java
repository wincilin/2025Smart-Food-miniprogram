package com.smartfood.backend.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.smartfood.backend.entity.FoodNutrition;
import java.util.List;

@Repository
public interface FoodNutritionRepository extends JpaRepository<FoodNutrition, Long> {
    
    boolean existsByName(String name); // 判断是否已存在某个食物名称
    // 使用LIKE进行模糊搜索
    @Query("SELECT f FROM FoodNutrition f WHERE f.name LIKE %:keyword%")
    List<FoodNutrition> searchByKeyword(@Param("keyword") String keyword);
} 