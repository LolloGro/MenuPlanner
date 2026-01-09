package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ReadMenuDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that manage menus.
 * This controller exposes HTTP endpoints for the user to read, add, update and delete a menu.
 * Validation and business logic is delegated to the MenuService.
 */

@RestController
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menus")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadMenuDto> getMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/menus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadMenuDto getMenuById(@PathVariable int id) {
        return menuService.getMenuById(id);
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
    public ReadMenuDto addMenus(@Valid @RequestBody MenuDto newMenu) {
        return menuService.addMenu(newMenu);
    }

    @PutMapping("/menus/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadMenuDto updateMenus(@PathVariable int id, @Valid @RequestBody MenuDto newMenu) {
        return menuService.updateMenu(id, newMenu);
    }

    @DeleteMapping("/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenus(@PathVariable int id) {
        menuService.deleteMenu(id);
    }
}
