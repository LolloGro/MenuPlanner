package com.lollo.menuplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lollo.menuplanner.TestAuditorConfig;
import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.ReadMealDto;
import com.lollo.menuplanner.entity.Ingredient;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Import({TestcontainersConfiguration.class, TestAuditorConfig.class})
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "test")
class RecipeControllerTest {

    @Autowired
    private RecipeRepository  recipeRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MealService mealService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mealRepository.deleteAll();
        Meal meal = new Meal();
        meal.setMealName("Veggie soup");
        meal.setMainIngredient("Carrots");
        meal.setMealType(MealType.LUNCH);
        meal.setTime(60);
        mealRepository.save(meal);
    }

    @Test
    void shouldReturnCreatedIfSaveIsSuccessful() throws Exception {
        int id = idForMeal();

        RecipeDto recipe = createRecipe("carrot", 2.0);

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

        RecipeDto recipe = createRecipe("carrot", 2.0);

        Recipe newRecipe = new Recipe();
        newRecipe.setMeal(meal);
        newRecipe.setIngredient(recipe.ingredients());
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

        RecipeDto recipe = createRecipe("carrot", 2.0);

        mockMvc.perform(post("/api/meals/{id}/recipes", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe))
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Meal not found"));
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
    int id  = idForMeal();

    Meal meal = mealRepository.findById(id).orElseThrow();

    RecipeDto carrotRecipe = createRecipe("carrot", 2.0);

        Recipe newRecipe = new Recipe();
        newRecipe.setMeal(meal);
        newRecipe.setIngredient(carrotRecipe.ingredients());
        newRecipe.setDescription(carrotRecipe.description());
        recipeRepository.save(newRecipe);

    RecipeDto potatoRecipe = createRecipe("potato", 5.0);

        mockMvc.perform(put("/api/meals/{id}/recipes", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(potatoRecipe))
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect( jsonPath("$.ingredients[0].ingredient").value("potato"));
    }

    @Test
    void shouldReturnErrorMessageIfRecipeDoesNotExist()  throws Exception {
        int id = idForMeal();

        RecipeDto carrotRecipe = createRecipe("carrot", 2.0);

        mockMvc.perform(put("/api/meals/{id}/recipes", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carrotRecipe))
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Recipe not found"));
    }

    @Test
    void shouldDeleteRecipe() throws Exception {
        int id = idForMeal();

        Meal meal = mealRepository.findById(id).orElseThrow();

        RecipeDto recipe = createRecipe("carrot", 2.0);

        Recipe newRecipe = new Recipe();
        newRecipe.setMeal(meal);
        newRecipe.setIngredient(recipe.ingredients());
        newRecipe.setDescription(recipe.description());
        recipeRepository.save(newRecipe);

        mockMvc.perform(delete("/api/meals/{id}/recipes", id)
                .with(csrf()))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnErrorMessageIfNoRecipeExists() throws Exception {
        int id = idForMeal();

        mockMvc.perform(delete("/api/meals/{id}/recipes", id)
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Recipe not found"));
    }

    public int idForMeal(){
        List<ReadMealDto> listOfMeals = mealService.getAllMeals();

        return listOfMeals.getFirst().id();
    }

    public RecipeDto createRecipe(String ingredient, double amount){
        Ingredient newIngredient = new Ingredient(ingredient, amount, "st");
        Ingredient onion = new Ingredient("onion", 1.0, "st");
        Ingredient water =  new Ingredient("water", 1.0, "L");

        List<Ingredient> ingredients = List.of(newIngredient, onion, water);

        return new RecipeDto(ingredients, "1. Chop 2. Boil");
    }


}
