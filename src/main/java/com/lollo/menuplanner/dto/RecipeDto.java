package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.Ingredient;
import jakarta.validation.Valid;

import java.util.List;

public record RecipeDto(
    @Valid
    List<Ingredient> ingredients,
    String description
) {
}
