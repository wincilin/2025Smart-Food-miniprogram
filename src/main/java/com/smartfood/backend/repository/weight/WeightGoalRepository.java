package com.smartfood.backend.repository.weight;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfood.backend.entity.User;
import com.smartfood.backend.entity.WeightGoal;

@Repository
public interface WeightGoalRepository extends JpaRepository<WeightGoal, Long> {

    Optional<WeightGoal> findByUser(User user);
    
}
