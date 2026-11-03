
package com.food.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.entity.Menu;
import com.food.repository.MenuRepository;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Add a new menu item
    public Menu addMenuItem(Menu menu) {
        return menuRepository.save(menu);
    }

    // Get all menu items
    public List<Menu> getAllMenuItems() {
        return menuRepository.findAll();
    }

    // Get menu item by ID
    public Menu getMenuItemById(Integer id) {
        return menuRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Menu item not found with ID: " + id));
    }

    // Delete menu item
    public void deleteMenuItem(Integer id) {
        Menu menu = getMenuItemById(id);
        menuRepository.delete(menu);
    }
}
