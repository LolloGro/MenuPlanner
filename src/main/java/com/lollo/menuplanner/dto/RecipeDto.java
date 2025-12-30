package com.lollo.menuplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RecipeDto(
    @NotBlank
    String ingredient,
    @Positive
    double amount,
    String measurement,
    String description
) {
}
