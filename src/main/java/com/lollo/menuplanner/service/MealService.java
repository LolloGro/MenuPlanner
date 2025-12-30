package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.exception.DuplicateMealsException;
import com.lollo.menuplanner.exception.NotFoundException;
import com.lollo.menuplanner.repository.MealRepository;
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
            .map(meal -> new CompleteMealDto(meal.getId(), meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime())).toList();
    }

    @Transactional
    public MealDto addMeal(MealDto mealDto) {

        String nameToUpperCase = capitalizeFirstLetter(mealDto.mealName());

        if(mealRepository.findMealByMealName(nameToUpperCase).isPresent()){
            throw new DuplicateMealsException(mealDto.mealName());
        }

        Meal meal = new Meal(mealDto.mealName(), mealDto.mainIngredient(), mealDto.mealType(), mealDto.time());

        mealRepository.save(meal);

        return new MealDto(meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime());
    }

    @Transactional
    public MealDto updateMeal(int id, MealDto mealDto) {
        Meal mealToUpdate = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal with id "+id+" not found"));

        String nameToUpperCase = capitalizeFirstLetter(mealDto.mealName());

        if(mealRepository.findMealByMealName(nameToUpperCase).isPresent()){
            throw new DuplicateMealsException(mealDto.mealName());
        }

        mealToUpdate.setMealName(mealDto.mealName());
        mealToUpdate.setMainIngredient(mealDto.mainIngredient());
        mealToUpdate.setMealType(mealDto.mealType());
        mealToUpdate.setTime(mealDto.time());
        mealRepository.save(mealToUpdate);

        return new MealDto(mealToUpdate.getMealName(), mealToUpdate.getMainIngredient(), mealToUpdate.getMealType(), mealToUpdate.getTime());
    }

    @Transactional
    public void deleteMeal(int id) {
        Optional<Meal> mealToDelete = mealRepository.findById(id);

        if(mealToDelete.isPresent()){
            mealRepository.delete(mealToDelete.get());
        }else {
            throw new NotFoundException("Meal with id "+id+" not found");
        }
    }

    public String capitalizeFirstLetter(String alter){

        if(alter == null || alter.isEmpty()){
            return alter;
        }

        return alter.substring(0, 1).toUpperCase() + alter.substring(1);
    }

}
