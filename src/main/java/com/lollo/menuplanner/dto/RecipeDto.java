package com.lollo.menuplanner.dto;

import java.util.List;

public record RecipeDto(
    List<Ingredient> ingredient,
    String description
) {
}
