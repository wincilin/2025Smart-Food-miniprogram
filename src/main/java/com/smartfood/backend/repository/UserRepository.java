package com.smartfood.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfood.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 这里可以添加一些自定义查询方法
    // 例如：根据openid查找用户
    Optional<User> findByOpenid(String openid);

    
}
