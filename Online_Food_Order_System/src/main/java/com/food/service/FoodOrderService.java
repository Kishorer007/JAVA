
package com.food.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.food.entity.FoodOrder;
import com.food.entity.Menu;
import com.food.repository.FoodOrderRepository;
import com.food.repository.MenuRepository;

@Service
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;
    private final MenuRepository menuRepository;

    public FoodOrderService(
            FoodOrderRepository foodOrderRepository,
            MenuRepository menuRepository) {

        this.foodOrderRepository = foodOrderRepository;
        this.menuRepository = menuRepository;
    }

    // Place customer order
    public FoodOrder placeOrder(FoodOrder order) {

        Integer itemId = order.getMenu().getItemId();

        Menu menu = menuRepository.findById(itemId)
                .orElseThrow(() ->
                    new RuntimeException("Menu item not found"));

        if (!menu.isAvailable()) {
            throw new RuntimeException("This food item is currently unavailable");
        }

        BigDecimal total = menu.getPrice()
                .multiply(BigDecimal.valueOf(order.getQuantity()));

        order.setMenu(menu);
        order.setTotalPrice(total);

        return foodOrderRepository.save(order);
    }

    // Get all customer orders
    public List<FoodOrder> getAllOrders() {
        return foodOrderRepository.findAll();
    }

    // Get order by ID
    public FoodOrder getOrderById(Integer id) {
        return foodOrderRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Order not found"));
    }

    // Delete order
    public void deleteOrder(Integer id) {
        FoodOrder order = getOrderById(id);
        foodOrderRepository.delete(order);
    }
}
