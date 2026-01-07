package com.lollo.menuplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenuDto(
    @NotBlank
    String menuName,
    @NotNull
    List<CompleteMealDto> menu) {
}
