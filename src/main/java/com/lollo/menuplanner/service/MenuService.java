package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.ReadMenuDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.entity.Meal;
import com.lollo.menuplanner.entity.MealOfMenu;
import com.lollo.menuplanner.entity.Menu;
import com.lollo.menuplanner.exception.NotFoundException;
import com.lollo.menuplanner.repository.MealRepository;
import com.lollo.menuplanner.repository.MenuRepository;
import jakarta.validation.Valid;
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
            .map(this::toReadMenuDto).toList();
    }

    public ReadMenuDto getMenuById(int id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu with id "+id+" not found"));

        return toReadMenuDto(menu);
    }

    @Transactional
    public ReadMenuDto addMenu(@Valid MenuDto newMenu) {

        Menu menu =  new Menu();
        setMenu(menu, newMenu);
        menuRepository.save(menu);

        return toReadMenuDto(menu);
    }

    @Transactional
    public ReadMenuDto updateMenu(int id, @Valid MenuDto newMenu) {

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not with "+id+" not found"));
        setMenu(menu, newMenu);
        menuRepository.save(menu);

        return toReadMenuDto(menu);

    }

    @Transactional
    public void deleteMenu(int id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu with id "+id+" not found"));

        menuRepository.delete(menu);
    }

     private ReadMenuDto toReadMenuDto(Menu menu) {
        return new ReadMenuDto(menu.getId(), menu.getMenuName(), menu.getMenu(), menu.getMenuCreatedDate());
     }
    /**
     * Method for reuse that finds meals by id.
     * Stores them in a map
     * Creates a List of MealOfMenu from saved map and the order from saved mealIds.
     */
    private void setMenu(Menu menu, MenuDto newMenu) {
        String menuName = capitalizeFirstLetter(newMenu.menuName().trim());

        List<Meal> meals = mealRepository.findAllById(newMenu.mealIds());

        if(meals.size() != newMenu.mealIds().size()) {
            throw new NotFoundException("All meals not found");
        }

        Map<Integer, Meal> mealsToAdd = meals.stream().collect(Collectors.toMap(Meal::getId, meal -> meal));

        List<MealOfMenu> mealsToMenu = newMenu.mealIds().stream()
            .map(mealId -> {
                Meal meal = mealsToAdd.get(mealId);
                return new MealOfMenu(
                    meal.getId(), meal.getMealName());

            }).toList();

        menu.setMenuName(menuName);
        menu.setMenu(mealsToMenu);
    }

}
