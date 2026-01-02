package com.lollo.menuplanner.dto;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Embeddable
public record Ingredient(
    @NotBlank
    String ingredient,
    @Positive
    Double amount,
    String measurement) {
}
