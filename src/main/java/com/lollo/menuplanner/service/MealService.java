package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.ReadMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.exception.DuplicateResourcesException;
import com.lollo.menuplanner.exception.NotFoundException;
import com.lollo.menuplanner.repository.MealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lollo.menuplanner.util.Capitalize.capitalizeFirstLetter;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<ReadMealDto> getAllMeals() {
        return mealRepository.findAll().stream()
            .map(meal -> new ReadMealDto(meal.getId(), meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime())).toList();
    }

    @Transactional
    public MealDto addMeal(MealDto mealDto) {

        String nameToUpperCase = capitalizeFirstLetter(mealDto.mealName());

        if(mealRepository.findMealByMealName(nameToUpperCase).isPresent()){
            throw new DuplicateResourcesException("Meal with name " + mealDto.mealName() + " already exists");
        }

        Meal meal = new Meal(mealDto.mealName(), mealDto.mainIngredient(), mealDto.mealType(), mealDto.time());

        mealRepository.save(meal);

        return new MealDto(meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime());
    }

    @Transactional
    public MealDto updateMeal(int id, MealDto mealDto) {
        Meal mealToUpdate = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal with id "+id+" not found"));

        String nameToUpperCase = capitalizeFirstLetter(mealDto.mealName());

        if(mealRepository.findMealByMealName(nameToUpperCase).filter(meal -> !meal.getId().equals(id)).isPresent()){
            throw new DuplicateResourcesException("Meal with name " + mealDto.mealName() + " already exists");
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
        Meal mealToDelete = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal with id "+id+" not found"));

        mealRepository.delete(mealToDelete);
    }

}
