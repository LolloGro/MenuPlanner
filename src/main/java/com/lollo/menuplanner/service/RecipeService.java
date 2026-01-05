package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.RecipeDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.Recipe;
import com.lollo.menuplanner.exception.DuplicateResourcesException;
import com.lollo.menuplanner.exception.NotFoundException;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeService {

    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;

    public RecipeService(MealRepository mealRepository, RecipeRepository recipeRepository) {
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public RecipeDto createRecipe(RecipeDto recipe, int id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal not found"));

        if(meal.getRecipe() != null) {
            throw new DuplicateResourcesException("Recipe already exists");
        }

        Recipe newRecipe = new Recipe();
        newRecipe.setMeal(meal);
        newRecipe.setIngredient(recipe.ingredients());
        newRecipe.setDescription(recipe.description());
        recipeRepository.save(newRecipe);

        return new RecipeDto(newRecipe.getIngredient(), newRecipe.getDescription());
    }

    @Transactional
    public RecipeDto updateRecipe(RecipeDto recipe, int id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal not found"));

        Recipe recipeToUpdate = meal.getRecipe();

        if(recipeToUpdate == null) {
            throw new NotFoundException("Recipe not found");
        }

        recipeToUpdate.setIngredient(recipe.ingredients());
        recipeToUpdate.setDescription(recipe.description());
        recipeRepository.save(recipeToUpdate);

        return new RecipeDto(recipeToUpdate.getIngredient(), recipeToUpdate.getDescription());
    }

    @Transactional
    public void deleteRecipe(int id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new NotFoundException("Meal not found"));

        if(meal.getRecipe() == null) {
            throw new NotFoundException("Recipe not found");
        }

        recipeRepository.delete(meal.getRecipe());
    }
}
