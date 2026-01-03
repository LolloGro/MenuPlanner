package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.RecipeDto;
import com.lollo.menuplanner.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meals/{id}")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes")
    public ResponseEntity<RecipeDto> addRecipe(@PathVariable Integer id, @RequestBody RecipeDto recipe) {
        RecipeDto savedRecipe = recipeService.createRecipe(recipe, id);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @PutMapping("/recipes")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Integer id, @RequestBody RecipeDto recipe) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(recipe, id);
      return ResponseEntity.status(HttpStatus.OK).body(updatedRecipe);
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
