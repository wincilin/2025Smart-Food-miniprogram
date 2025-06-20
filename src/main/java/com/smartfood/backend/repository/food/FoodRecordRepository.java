package com.smartfood.backend.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartfood.backend.entity.FoodRecord;
import java.util.List;

@Repository
public interface FoodRecordRepository extends JpaRepository<FoodRecord, Long> {
    List<FoodRecord> findByOpenidOrderByRecordTimeDesc(String openid);
    List<FoodRecord> findByOpenidAndRecordTimeStartingWith(String openid, String datePrefix);
} 