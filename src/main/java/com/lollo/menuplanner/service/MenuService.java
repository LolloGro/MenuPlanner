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
    private final LoggedInUser loggedInUser;

    public MenuService(MenuRepository menuRepository,  MealRepository mealRepository, LoggedInUser loggedInUser) {
        this.menuRepository = menuRepository;
        this.mealRepository = mealRepository;
        this.loggedInUser = loggedInUser;
    }

    public List<ReadMenuDto> getAllMenus() {
        return menuRepository.findByCreatedBy(loggedInUser.getProviderId()).stream()
            .map(this::toReadMenuDto).toList();
    }

    public ReadMenuDto getMenuById(int id) {
        Menu menu = menuRepository.findByIdAndCreatedBy(id, loggedInUser.getProviderId()).orElseThrow(() -> new NotFoundException("Menu with id "+id+" not found"));

        return toReadMenuDto(menu);
    }

    @Transactional
    public ReadMenuDto addMenu(MenuDto newMenu) {

        Menu menu =  new Menu();
        setMenu(menu, newMenu);
        menuRepository.save(menu);

        return toReadMenuDto(menu);
    }

    @Transactional
    public ReadMenuDto updateMenu(int id, MenuDto newMenu) {

        Menu menu = menuRepository.findByIdAndCreatedBy(id, loggedInUser.getProviderId()).orElseThrow(() -> new NotFoundException("Menu with "+id+" not found"));
        setMenu(menu, newMenu);
        menuRepository.save(menu);

        return toReadMenuDto(menu);

    }

    @Transactional
    public void deleteMenu(int id) {
        Menu menu = menuRepository.findByIdAndCreatedBy(id, loggedInUser.getProviderId()).orElseThrow(() -> new NotFoundException("Menu with id "+id+" not found"));
        menuRepository.delete(menu);
    }

     private ReadMenuDto toReadMenuDto(Menu menu) {
        return new ReadMenuDto(menu.getId(), menu.getMenuName(), menu.getMenu(), menu.getMenuCreatedDate());
     }
    /**
     * Method for reuse that finds meals by meal id and created by id.
     * Distinct count handles duplicate meals
     * Stores them in a map
     * Creates a List of MealOfMenu from saved map and the order from saved mealIds.
     */
    private void setMenu(Menu menu, MenuDto newMenu) {
        String menuName = capitalizeFirstLetter(newMenu.menuName().trim());

        List<Integer> idForNewMeal = newMenu.mealIds();
        long distinctCount = idForNewMeal.stream().distinct().count();

        List<Meal> allMeals = mealRepository.findByCreatedBy(loggedInUser.getProviderId());
        List<Meal> meals = allMeals.stream().filter(meal -> idForNewMeal.contains(meal.getId())).toList();

        if(meals.size() != distinctCount) {
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
