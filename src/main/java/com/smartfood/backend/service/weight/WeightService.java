package com.smartfood.backend.service.weight;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.weight.WeightRequestDTO;
import com.smartfood.backend.dto.weight.WeightResponseDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.model.WeightData;
import com.smartfood.backend.repository.weight.WeightRepository;

@Service
public class WeightService {
    
    @Autowired
    private WeightRepository weightRepository;

    public void saveWeight(WeightRequestDTO weightDTO, User user) {
        WeightData weightData = new WeightData();
        weightData.setWeight(weightDTO.getWeight());
        weightData.setDate(LocalDate.parse(weightDTO.getDate()));
        weightData.setUser(user);
        weightRepository.save(weightData); 
    }

    public List<WeightResponseDTO> getWeights(User user) {
        //List<WeightDTO> weightList = weightRepository.getUserWeight(user);
        return weightRepository.findByUser(user).stream()
                .map(w -> new WeightResponseDTO(w.getId(),w.getWeight(),w.getDate().toString(),w.getUser().getOpenid()))
                .collect(Collectors.toList());
    }
}
