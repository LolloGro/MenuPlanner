package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.service.MealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals")
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }

}
