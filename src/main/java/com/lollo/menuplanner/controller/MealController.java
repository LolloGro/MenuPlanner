package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ReadMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReadMealDto>>  getMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("meals/{id}")
    public ResponseEntity<ReadMealDto> getMeal(@PathVariable int id) {
        return ResponseEntity.ok(mealService.getMeal(id));
    }

    @PostMapping("/meals")
    ResponseEntity<MealDto> addMeal(@Valid @RequestBody MealDto mealDto) {
        MealDto meal =  mealService.addMeal(mealDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(meal);
    }

    @PutMapping("/meals/{id}")
    ResponseEntity<MealDto> updateMeal(@PathVariable int id, @Valid @RequestBody MealDto mealDto) {
        MealDto updatedMeal =  mealService.updateMeal(id, mealDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMeal);
    }

    @DeleteMapping("/meals/{id}")
    ResponseEntity<Void> deleteMeal(@PathVariable int id){
        mealService.deleteMeal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
