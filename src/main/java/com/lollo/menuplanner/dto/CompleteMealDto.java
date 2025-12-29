package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.MealType;
import com.lollo.menuplanner.entity.Recipe;

public record CompleteMealDto(
    Integer id,
    String mealName,
    String mainIngredient,
    MealType mealType,
    Integer time,
    Recipe recipe
) {
}
