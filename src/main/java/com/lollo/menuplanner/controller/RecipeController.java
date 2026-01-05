package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.RecipeDto;
import com.lollo.menuplanner.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that manage recipe to a specific meal
 * This controller exposes HTTP endpoints for the user to add, update and delete a recipe.
 * Validation and business logic is delegated to the RecipeService.
 */

@RestController
@RequestMapping("/api/meals/{id}")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes")
    public ResponseEntity<RecipeDto> addRecipe(@PathVariable Integer id, @Valid @RequestBody RecipeDto recipe) {
        RecipeDto savedRecipe = recipeService.createRecipe(recipe, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @PutMapping("/recipes")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Integer id, @Valid @RequestBody RecipeDto recipe) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(recipe, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRecipe);
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
