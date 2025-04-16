package com.smartfood.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.smartfood.backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 这里可以添加一些自定义查询方法
    // 例如：根据openid查找用户
    Optional<User> findByOpenid(String openid);

    
}
