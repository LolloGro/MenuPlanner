package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.MealType;

public record ReadMealDto(
    Integer id,
    String mealName,
    String mainIngredient,
    MealType mealType,
    Integer time
) {
}
