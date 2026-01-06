package com.lollo.menuplanner.controller;

import com.lollo.menuplanner.dto.ListOfMenuDto;
import com.lollo.menuplanner.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
