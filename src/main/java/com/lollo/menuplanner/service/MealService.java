package com.lollo.menuplanner.service;

import com.lollo.menuplanner.repository.MealRepository;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    private MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

}
