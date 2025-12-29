package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<MealDto> getAllMeals() {
        return mealRepository.findAll().stream()
            .map(meal -> new MealDto(meal.getMealName(), meal.getMainIngredient(), meal.getMealType(), meal.getTime())).toList();
    }


}
