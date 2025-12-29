package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.entity.Meal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends ListCrudRepository<Meal, Integer> {
}
