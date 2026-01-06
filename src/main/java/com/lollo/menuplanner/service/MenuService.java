package com.lollo.menuplanner.service;

import com.lollo.menuplanner.dto.ListOfMenuDto;
import com.lollo.menuplanner.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
