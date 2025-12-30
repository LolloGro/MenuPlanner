package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.exception.DuplicateMeals;
import com.lollo.menuplanner.exception.NotFound;
import com.lollo.menuplanner.repository.MealRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<CompleteMealDto> getAllMeals() {
        return mealRepository.findAll().stream()
            .map(meal -> new CompleteMealDto(meal.getId(), meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime(), meal.getRecipe())).toList();
    }

    @Transactional
    public ResponseEntity<Meal> addMeal(MealDto mealDto) {

        String nameToUpperCase = mealDto.mealName().substring(0, 1).toUpperCase() + mealDto.mealName().substring(1);

        if(mealRepository.findMealByMealName(nameToUpperCase).isPresent()){
            throw new DuplicateMeals(mealDto.mealName());
        }

        Meal meal = new Meal();
        meal.setMealName(mealDto.mealName());
        meal.setMainIngredient(mealDto.mainIngredient());
        meal.setMealType(mealDto.mealType());
        meal.setTime(mealDto.time());
        mealRepository.save(meal);

        return ResponseEntity.status(HttpStatus.CREATED).body(meal);
    }

    @Transactional
    public ResponseEntity<Meal> updateMeal(int id, MealDto mealDto) {
        Optional<Meal> mealToUpdate = mealRepository.findById(id);

        if(mealToUpdate.isPresent()){
            Meal  updatedMeal = new Meal();
            updatedMeal.setMealName(mealDto.mealName());
            updatedMeal.setMainIngredient(mealDto.mainIngredient());
            updatedMeal.setMealType(mealDto.mealType());
            updatedMeal.setTime(mealDto.time());
            mealRepository.save(updatedMeal);

            return ResponseEntity.status(HttpStatus.OK).body(updatedMeal);
        }else{
            throw new NotFound("Meal with id "+id+" not found");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteMeal(int id) {
        Optional<Meal> mealToDelete = mealRepository.findById(id);

        if(mealToDelete.isPresent()){
            mealRepository.delete(mealToDelete.get());
            return ResponseEntity.status(HttpStatus.OK).body(mealToDelete.get());
        }else {
            throw new NotFound("Meal with id "+id+" not found");
        }
    }

}
