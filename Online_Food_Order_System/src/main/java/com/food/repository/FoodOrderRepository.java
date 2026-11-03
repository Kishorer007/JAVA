
package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.food.entity.FoodOrder;

public interface FoodOrderRepository
        extends JpaRepository<FoodOrder, Integer> {

}
