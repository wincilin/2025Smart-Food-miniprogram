package com.smartfood.backend.repository;

import com.smartfood.backend.entity.PhotoRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PhotoRecordRepository extends JpaRepository<PhotoRecord, Long>{
    List<PhotoRecord> findByOpenidOrderByCreatedAtDesc(String openid); // 根据用户ID查找图片记录
}
