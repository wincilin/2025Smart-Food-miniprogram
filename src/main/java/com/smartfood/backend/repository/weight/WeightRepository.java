package com.smartfood.backend.repository.weight;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfood.backend.model.User;
import com.smartfood.backend.model.WeightData;

@Repository
public interface WeightRepository extends JpaRepository<WeightData, Long> {

    List<WeightData> findByUser(User user);
}
