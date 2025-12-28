package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MealDto(
    @NotBlank
    String mealName,
    @NotBlank
    String mainIngredient,
    @NotNull
    MealType mealType,
    @NotNull
    @Min(value = 1)
    Integer time
) {
}
