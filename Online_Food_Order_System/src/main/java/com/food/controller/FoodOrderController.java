
package com.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.food.entity.FoodOrder;
import com.food.service.FoodOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    // Customer places an order
    @PostMapping
    public FoodOrder placeOrder(@Valid @RequestBody FoodOrder order) {
        return foodOrderService.placeOrder(order);
    }

    // View all orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return foodOrderService.getAllOrders();
    }

    // View order by ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Integer id) {
        return foodOrderService.getOrderById(id);
    }

    // Cancel order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        foodOrderService.deleteOrder(id);
        return "Order cancelled successfully";
    }
}
