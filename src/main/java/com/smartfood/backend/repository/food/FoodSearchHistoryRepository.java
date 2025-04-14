package com.smartfood.backend.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodSearchHistoryRepository extends JpaRepository<String , Long>{

}
