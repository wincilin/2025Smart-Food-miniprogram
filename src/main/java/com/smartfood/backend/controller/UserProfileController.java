package com.smartfood.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.dto.user.UpdateUserProfileDTO;
import com.smartfood.backend.dto.user.UserInfoDTO;
import com.smartfood.backend.model.User;
import com.smartfood.backend.service.UserProfileService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户信息管理", description = "获取和更新用户个人信息相关接口")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    /**
     * 
     * @param user 当前用户，由@AuthenticationPrincipal注入
     * @return 更新完的用户信息
     */

    @PostMapping("/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setNickName(user.getUserName());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setGender(user.getGender());
        return ResponseEntity.ok(userInfo);
    }
    
    /**
     * 更新用户信息
     * @param updateUserProfileDTO 更新用户信息所需字段
     * @param user 当前用户，由@AuthenticationPrincipal注入
     * @return 修改结果封装为 {@link ApiResponse}
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateInfo(@RequestBody @Valid UpdateUserProfileDTO updateUserProfileDTO,
        @AuthenticationPrincipal User user) {
            userProfileService.updateUserProfile(updateUserProfileDTO, user);
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully update profile", null));
    }
}
