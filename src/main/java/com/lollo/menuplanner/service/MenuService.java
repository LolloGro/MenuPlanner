package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.ReadMenuDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealOfMenu;
import com.lollo.menuplanner.entity.Menu;
import com.lollo.menuplanner.exception.NotFoundException;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lollo.menuplanner.util.Capitalize.capitalizeFirstLetter;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;

    public MenuService(MenuRepository menuRepository,  MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.mealRepository = mealRepository;
    }

    public List<ReadMenuDto> getAllMenus() {
        return menuRepository.findAll().stream()
            .map(menu -> new ReadMenuDto(menu.getId(), menu.getMenuName(), menu.getMenu(), menu.getMenuCreatedDate())).toList();
    }

    @Transactional
    public ReadMenuDto addMenu(MenuDto newMenu) {
        String menuName = capitalizeFirstLetter(newMenu.menuName());

        List<Meal> meals = mealRepository.findAllById(newMenu.mealId());

        if(meals.size() != newMenu.mealId().size()) {
            throw new NotFoundException("Meals not found");
        }

        Map<Integer, Meal> mealsToAdd = meals.stream().collect(Collectors.toMap(Meal::getId, meal -> meal));

        List<MealOfMenu> mealsToMenu = newMenu.mealId().stream()
            .map(mealId -> {
                Meal meal = mealsToAdd.get(mealId);
                return new MealOfMenu(
                    meal.getId(), meal.getMealName());

            }).toList();

        Menu menuToSave =  new Menu();
        menuToSave.setMenuName(menuName);
        menuToSave.setMenu(mealsToMenu);

        menuRepository.save(menuToSave);

        return new ReadMenuDto(menuToSave.getId(), menuToSave.getMenuName(), menuToSave.getMenu(), menuToSave.getMenuCreatedDate());
    }
}
