package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
