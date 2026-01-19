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

    @GetMapping("/recipes")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable int id){
        return recipeService.getRecipe(id);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto addRecipe(@PathVariable Integer id, @Valid @RequestBody RecipeDto recipe) {
        return recipeService.createRecipe(recipe, id);
    }

    @PutMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto updateRecipe(@PathVariable Integer id, @Valid @RequestBody RecipeDto recipe) {
        return recipeService.updateRecipe(recipe, id);
    }

    @DeleteMapping("/recipes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
    }

}
