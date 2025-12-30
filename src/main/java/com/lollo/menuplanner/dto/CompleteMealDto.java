package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.MealType;

public record CompleteMealDto(
    Integer id,
    String mealName,
    String mainIngredient,
    MealType mealType,
    Integer time
) {
}
