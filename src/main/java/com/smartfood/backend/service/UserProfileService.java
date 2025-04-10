package com.smartfood.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.user.UpdateUserProfileDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.repository.UserRepository;

@Service
public class UserProfileService {
    @Autowired
    private UserRepository userRepository;

    public void updateUserProfile(UpdateUserProfileDTO userProfileDTO,
        User user) {
            user.setUserName(userProfileDTO.getNickName());
            user.setBirthday(userProfileDTO.getBirthday());
            user.setGender(userProfileDTO.getGender());
            userRepository.save(user);
    }
}
