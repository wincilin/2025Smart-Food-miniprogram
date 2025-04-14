package com.smartfood.backend.dto.weight;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightDataGetDTO {
    @NotNull
    @Schema(description = "分页的页数")
    private int page;

    @NotNull
    @Schema(description = "分页的每页大小")
    private int size;
}
