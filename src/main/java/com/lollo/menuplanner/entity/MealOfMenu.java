package com.lollo.menuplanner.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record MealOfMenu(
    @NotNull
    Integer id,
    @NotBlank
    String mealName
    ) {
}
