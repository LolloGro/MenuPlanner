package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ReadMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that manage meals.
 * This controller exposes HTTP endpoints for the user to read, add, update and delete a meal.
 * Validation and business logic is delegated to the MealService.
 */

@RestController
@RequestMapping("/api")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadMealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadMealDto getMeal(@PathVariable int id) {
        return mealService.getMeal(id);
    }

    @PostMapping("/meals")
    @ResponseStatus(HttpStatus.CREATED)
    public MealDto addMeal(@Valid @RequestBody MealDto mealDto) {
       return mealService.addMeal(mealDto);
    }

    @PutMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MealDto updateMeal(@PathVariable int id, @Valid @RequestBody MealDto mealDto) {
        return mealService.updateMeal(id, mealDto);
    }

    @DeleteMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable int id){
        mealService.deleteMeal(id);
    }

}
