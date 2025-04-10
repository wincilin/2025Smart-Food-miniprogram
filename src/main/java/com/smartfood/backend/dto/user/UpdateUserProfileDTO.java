package com.smartfood.backend.dto.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDTO {
    private String nickName;
    private LocalDate birthday;
    private String gender;
}
