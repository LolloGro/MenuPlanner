package com.lollo.menuplanner.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MenuDto(
    @NotBlank
    String menuName,
    @NotBlank
    List<CompleteMealDto> menu) {
}
