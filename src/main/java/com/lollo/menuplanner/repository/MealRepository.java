package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends ListCrudRepository<Meal, Integer> {

    Optional<MealDto> findMealByMealName(String mealName);

}
