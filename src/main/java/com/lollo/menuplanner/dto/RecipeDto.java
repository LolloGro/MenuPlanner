package com.lollo.menuplanner.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeDto(
    @NotBlank
    String ingredient,
    double amount,
    String measurement,
    String description
) {
}
