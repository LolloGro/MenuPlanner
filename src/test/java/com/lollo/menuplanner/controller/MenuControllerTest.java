package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.CompleteMealDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.entity.MealType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getMenu() throws Exception {
        mockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatusCreated() throws Exception {
        MenuDto menu = createMenu();

        mockMvc.perform(post("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menu))
                .with(csrf()))
            .andExpect(status().isCreated());
    }

    public MenuDto createMenu(){
        CompleteMealDto mealOne = new CompleteMealDto(1, "Meatballs", "meat", MealType.DINNER, 30);
        CompleteMealDto mealTwo = new CompleteMealDto(2, "Carrot soup", "Carrot", MealType.DINNER, 45);
        List<CompleteMealDto> meals = List.of(mealOne, mealTwo);

        return new MenuDto("Week one", meals);
    }
}
