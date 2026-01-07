package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.ListOfMenuDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.entity.MealOfMenu;
import com.lollo.menuplanner.entity.Menu;
import com.lollo.menuplanner.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lollo.menuplanner.util.Capitalize.capitalizeFirstLetter;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<ListOfMenuDto> getAllMenus() {
        return menuRepository.findAll().stream()
            .map(menu -> new ListOfMenuDto(menu.getId(), menu.getMenuName(), menu.getMenu(), menu.getMenuCreatedDate())).toList();
    }

    @Transactional
    public ListOfMenuDto addMenu(MenuDto newMenu) {
        String menuName = capitalizeFirstLetter(newMenu.menuName());

        List<MealOfMenu> mapMenu = newMenu.menu().stream()
            .map(meal -> new MealOfMenu(meal.id(), meal.mealName())).toList();

        Menu menuToSave =  new Menu();
        menuToSave.setMenuName(menuName);
        menuToSave.setMenu(mapMenu);

        menuRepository.save(menuToSave);

        return new ListOfMenuDto(menuToSave.getId(), menuToSave.getMenuName(), menuToSave.getMenu(), menuToSave.getMenuCreatedDate());
    }
}
