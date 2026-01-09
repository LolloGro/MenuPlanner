package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.dto.ReadMenuDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealOfMenu;
import com.lollo.menuplanner.entity.MealType;
import com.lollo.menuplanner.entity.Menu;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.repository.MenuRepository;
import com.lollo.menuplanner.service.MenuService;
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
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        mealRepository.deleteAll();
        Meal soup = new Meal("Veggie soup", "Carrots", MealType.LUNCH, 60);
        Meal meatballs = new Meal("Meatballs", "Meat", MealType.LUNCH, 60);
        mealRepository.saveAll(List.of(soup, meatballs));

        menuRepository.deleteAll();
        MealOfMenu mealOne = new MealOfMenu(10, "Soup");
        MealOfMenu mealTwo = new MealOfMenu(20, "Potato");

        List<MealOfMenu> meals = List.of(mealOne, mealTwo);

        Menu menu = new Menu();
        menu.setMenuName("Week one");
        menu.setMenu(meals);

        menuRepository.save(menu);
    }

    @Test
    void shouldReturnOkForGetAllMenus() throws Exception {
        mockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOkForGetMenuById() throws Exception {
        List<ReadMenuDto> listOfMenus = menuService.getAllMenus();

        Integer id = listOfMenus.getFirst().id();

        mockMvc.perform(get("/api/menus/{id}", id))
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

    @Test
    void shouldReturnStatusNoContentWhenDelete() throws  Exception {
        List<ReadMenuDto> listOfMenus = menuService.getAllMenus();

        Integer id = listOfMenus.getFirst().id();

        mockMvc.perform(delete("/api/menus/{id}", id)
                .with(csrf()))
            .andExpect(status().isNoContent());

    }

    @Test
    void shouldReturnErrorMessageWhenMenuToDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/menus/150")
                .with(csrf()))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Menu with id 150 not found"));
    }

    public MenuDto createMenu(){
        List<Meal> meals = mealRepository.findAll();
        List<Integer> mealId = meals.stream()
            .map(Meal::getId).toList();

        return new MenuDto("Week two", mealId);
    }
}
