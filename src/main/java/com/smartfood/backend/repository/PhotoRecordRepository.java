package com.smartfood.backend.repository;

import com.smartfood.backend.entity.PhotoRecord;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PhotoRecordRepository extends JpaRepository<PhotoRecord, Long>{
    Page<PhotoRecord> findByOpenidOrderByCreatedAtDesc(String openid,Pageable pageable); // 根据用户ID查找图片记录
}
