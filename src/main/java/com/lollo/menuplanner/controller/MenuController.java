package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ListOfMenuDto;
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
    public ResponseEntity<List<ListOfMenuDto>> getMenus() {
        return ResponseEntity.ok(menuService.getAllMenus());

    }

    @PostMapping("/menus")
    public ResponseEntity<ListOfMenuDto> addMenus(@Valid @RequestBody MenuDto newMenu) {
         ListOfMenuDto createdMenu = menuService.addMenu(newMenu);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }
}
