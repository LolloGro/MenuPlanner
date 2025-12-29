package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals")
    public List<CompleteMealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @PostMapping("/meals")
    ResponseEntity<Meal> addMeal(@Valid @RequestBody MealDto mealDto) {
       return mealService.addMeal(mealDto);
    }

}
