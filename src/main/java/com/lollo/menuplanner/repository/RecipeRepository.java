package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.entity.Recipe;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends ListCrudRepository<Recipe, Integer> {
    Optional<Recipe> findByIdAndCreatedBy(int id, String createdBy);
}
