package com.smartfood.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 
 */
@Data
@AllArgsConstructor
public class TokenResponseDTO {
    
    @NotNull
    String token;
}
