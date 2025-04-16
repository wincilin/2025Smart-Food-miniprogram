package com.smartfood.backend.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.user.UpdateUserProfileDTO;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.UserRepository;

@Service
public class UserProfileService {
    @Autowired
    private UserRepository userRepository;

    public void updateUserProfile(UpdateUserProfileDTO userProfileDTO,
        User user) {
            user.setUserName(userProfileDTO.getUserName());
            user.setBirthdate(userProfileDTO.getBirthdate());
            user.setGender(userProfileDTO.getGender());
            user.setHeight(userProfileDTO.getHeight());
            userRepository.save(user);
    }
}
