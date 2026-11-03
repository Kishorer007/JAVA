
package com.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.food.entity.Menu;
import com.food.service.MenuService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Add menu item
    @PostMapping
    public Menu addMenuItem(@Valid @RequestBody Menu menu) {
        return menuService.addMenuItem(menu);
    }

    // Display all menu items
    @GetMapping
    public List<Menu> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    // Get menu item by ID
    @GetMapping("/{id}")
    public Menu getMenuItemById(@PathVariable Integer id) {
        return menuService.getMenuItemById(id);
    }

    // Delete menu item
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Integer id) {
        menuService.deleteMenuItem(id);
        return "Menu item deleted successfully";
    }
}
