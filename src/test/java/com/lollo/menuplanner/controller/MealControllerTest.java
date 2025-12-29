package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.MealDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealType;
import com.lollo.menuplanner.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;

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
        List<MealDto> meal = objectMapper.readValue(result, new TypeReference<>(){});

        assertNotNull(meal);
        assertEquals(1, meal.size());
    }

}
