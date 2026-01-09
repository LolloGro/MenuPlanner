package com.lollo.menuplanner.dto;

import com.lollo.menuplanner.entity.MealOfMenu;

import java.time.LocalDateTime;
import java.util.List;

public record ReadMenuDto(
    Integer id,
    String menuName,
    List<MealOfMenu> meals,
    LocalDateTime menuCreatedDate
) {
}
