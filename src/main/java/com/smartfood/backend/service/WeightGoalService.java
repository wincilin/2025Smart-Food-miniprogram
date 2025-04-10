package com.smartfood.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.weight.WeightGoalRequestDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.model.WeightGoal;
import com.smartfood.backend.repository.WeightGoalRepository;

@Service

public class WeightGoalService {
    @Autowired
    private WeightGoalRepository weightGoalRepository;

    public void saveWeightGoal(WeightGoalRequestDTO weightGoalRequestDTO, User user){
        Optional<WeightGoal> existingGoal = weightGoalRepository.findByUser(user);
        WeightGoal goal = existingGoal.orElse(new WeightGoal());

        goal.setTargetWeight(null == weightGoalRequestDTO.getTargetWeight() ? null : weightGoalRequestDTO.getTargetWeight());
        goal.setStartWeight(null == weightGoalRequestDTO.getStartWeight() ? null : weightGoalRequestDTO.getStartWeight());
        goal.setCurrentWeight(null == weightGoalRequestDTO.getCurrentWeight() ? null : weightGoalRequestDTO.getCurrentWeight());

        weightGoalRepository.save(goal);
    }

    public WeightGoalRequestDTO getWeightGoal(User user) {
        Optional<WeightGoal> existingGoal = weightGoalRepository.findByUser(user);
        if (existingGoal.isPresent()) {
            WeightGoal goal = existingGoal.get();
            return new WeightGoalRequestDTO(
                goal.getTargetWeight() == null ? 0.0 : goal.getTargetWeight(),
                goal.getStartWeight() == null ? 0.0 : goal.getStartWeight(),
                goal.getCurrentWeight() == null ? 0.0 : goal.getCurrentWeight()
            );
            
        } else {
           return new WeightGoalRequestDTO(0.0, 0.0, 0.0);
        }
    }
}
