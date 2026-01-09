package com.lollo.menuplanner.entity;

import com.lollo.menuplanner.TestcontainersConfiguration;
import com.lollo.menuplanner.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MenuTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void shouldSetDate() throws Exception {
        MealOfMenu mealOne = new MealOfMenu(1, "Veggie soup");
        MealOfMenu mealTwo = new MealOfMenu(2, "Meatballs");

        List<MealOfMenu> meals = List.of(mealOne, mealTwo);

        Menu menu = new Menu();
        menu.setMenuName("Week one");
        menu.setMenu(meals);

        menuRepository.save(menu);

        assertNotNull(menu.getMenuCreatedDate());

    }
}
