package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.entity.Meal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends ListCrudRepository<Meal, Integer> {
    List<Meal> findByCreatedBy(String createdBy);
    Optional<Meal> findByIdAndCreatedBy(int id, String createdBy);
    Optional<Meal> findMealByMealNameAndCreatedBy(String mealName, String createdBy);
}

