package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealType;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser
class MealControllerTest{

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealRepository.deleteAll();
        Meal meal = new Meal("Veggie soup", "Carrots", MealType.LUNCH, 60);
        mealRepository.save(meal);
    }

    @Test
    void shouldReturnResponseOK() throws Exception{
       mockMvc.perform(get("/api/meals"))
           .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllMeals() throws Exception{
        MvcResult response = this.mockMvc.perform(get("/api/meals"))
            .andReturn();

        String result = response.getResponse().getContentAsString();
        List<CompleteMealDto> meal = objectMapper.readValue(result, new TypeReference<>(){});

        assertNotNull(meal);
        assertEquals(1, meal.size());
    }

    @Test
    void shouldReturnStatusCreated() throws Exception{
        MealDto meal = new MealDto("Pulled pork", "pork", MealType.DINNER, 180);

        mockMvc.perform(post("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(meal))
                .with(csrf()))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldChangeNameCapitalizeFirstLetter() throws Exception {
        MealDto meal = new MealDto("pancake", "egg",  MealType.LUNCH, 30);

        mockMvc.perform(post("/api/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meal))
                .with(csrf()))
            .andExpect(jsonPath("$.mealName").value("Pancake"));
    }

    @Test
    void shouldReturnErrorMessagesIfMealNameAlreadyExist() throws Exception {
        MealDto meal = new MealDto("Veggie soup", "Carrots", MealType.LUNCH, 60);

        mockMvc.perform(post("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(meal))
            .with(csrf()))
            .andExpect(status().isConflict())
            .andExpect(content().string("Meal with name Veggie soup already exists"));
    }

    @Test
    void shouldUpdatedMeal() throws Exception {
        List<CompleteMealDto> listOfMeals = mealService.getAllMeals();

        Integer id = listOfMeals.getFirst().id();

        MealDto meal = new MealDto("Carrot cake", "Carrots", MealType.DESSERT, 60);

        mockMvc.perform(put("/api/meals/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(meal))
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mealName").value("Carrot cake"));
    }

    @Test
    void shouldReturnErrorMessageWhenMealToUpdateNotFound() throws Exception {
        MealDto meal = new MealDto("Carrot cake", "Carrots", MealType.DESSERT, 60);

        mockMvc.perform(put("/api/meals/150")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(meal))
            .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Meal with id 150 not found"));
    }

    @Test
    void shouldDeleteMeal() throws Exception {
        List<CompleteMealDto> listOfMeals = mealService.getAllMeals();

        Integer id = listOfMeals.getFirst().id();

        mockMvc.perform(delete("/api/meals/{id}", id)
            .with(csrf()))
            .andExpect(status().isOk());
    }

     @Test
    void shouldReturnErrorMessageWhenMealToDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/meals/150")
            .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Meal with id 150 not found"));
     }
}
