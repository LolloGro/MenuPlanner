package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ReadMenuDto;
import com.lollo.menuplanner.dto.MenuDto;
import com.lollo.menuplanner.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menus")
    public ResponseEntity<List<ReadMenuDto>> getMenus() {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.getAllMenus());

    }

    @GetMapping("/menus/{id}")
    public ResponseEntity<ReadMenuDto> getMenuById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenuById(id));
    }

    @PostMapping("/menus")
    public ResponseEntity<ReadMenuDto> addMenus(@Valid @RequestBody MenuDto newMenu) {
         ReadMenuDto createdMenu = menuService.addMenu(newMenu);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<ReadMenuDto> updateMenus(@PathVariable int id, @Valid @RequestBody MenuDto newMenu) {
        ReadMenuDto updatedMenu = menuService.updateMenu(id, newMenu);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMenu);
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<Void> deleteMenus(@PathVariable int id) {
        menuService.deleteMenu(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
