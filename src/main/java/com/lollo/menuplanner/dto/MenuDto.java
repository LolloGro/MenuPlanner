package com.lollo.menuplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record MenuDto(
    @NotBlank
    String menuName,
    @NotEmpty
    List<@Positive @NotNull Integer> mealIds) {
}
