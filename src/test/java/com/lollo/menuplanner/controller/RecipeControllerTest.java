package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.Ingredient;
import com.lollo.menuplanner.dto.RecipeDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealType;
import com.lollo.menuplanner.entity.Recipe;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.repository.RecipeRepository;
import com.lollo.menuplanner.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser
class RecipeControllerTest {

    @Autowired
    private RecipeRepository  recipeRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MealService mealService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mealRepository.deleteAll();
        Meal meal = new Meal("Veggie soup", "Carrots", MealType.LUNCH, 60);
        mealRepository.save(meal);
    }

    @Test
    void shouldReturnCreatedIfSaveIsSuccessful() throws Exception {
        int id = idForMeal();

        Ingredient carrot = new Ingredient("carrot", 3.0, "st");
        Ingredient onion = new Ingredient("onion", 1.0, "st");
        Ingredient water =  new Ingredient("water", 1.0, "L");

        List<Ingredient> ingredients = List.of(carrot, onion, water);

        RecipeDto recipe = new RecipeDto(ingredients, "1. Chop 2. Boil");

        mockMvc.perform(post("/api/meals/{id}/recipes", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(recipe))
            .with(csrf()))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorMessageIfRecipeAlreadyExist()  throws Exception {
        int id = idForMeal();
        Meal meal = mealRepository.findById(id).orElseThrow();

        Ingredient carrot = new Ingredient("carrot", 3.0, "st");
        Ingredient onion = new Ingredient("onion", 1.0, "st");
        Ingredient water =  new Ingredient("water", 1.0, "L");

        List<Ingredient> ingredients = List.of(carrot, onion, water);

        RecipeDto recipe = new RecipeDto(ingredients, "1. Chop 2. Boil");

        Recipe newRecipe = new Recipe();
        newRecipe.setMeal(meal);
        newRecipe.setIngredient(recipe.ingredient());
        newRecipe.setDescription(recipe.description());
        recipeRepository.save(newRecipe);

        mockMvc.perform(post("/api/meals/{id}/recipes", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe))
                .with(csrf()))
            .andExpect(status().isConflict())
            .andExpect(content().string("Recipe already exists"));
    }

    @Test
    void shouldReturnErrorMessageIfMealDontExist()  throws Exception {
        int id = 150;

        Ingredient carrot = new Ingredient("carrot", 3.0, "st");
        Ingredient onion = new Ingredient("onion", 1.0, "st");
        Ingredient water =  new Ingredient("water", 1.0, "L");

        List<Ingredient> ingredients = List.of(carrot, onion, water);

        RecipeDto recipe = new RecipeDto(ingredients, "1. Chop 2. Boil");

        mockMvc.perform(post("/api/meals/{id}/recipes", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe))
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Meal not found"));
    }

    public int idForMeal(){
        List<CompleteMealDto> listOfMeals = mealService.getAllMeals();

        return listOfMeals.getFirst().id();
    }

}
