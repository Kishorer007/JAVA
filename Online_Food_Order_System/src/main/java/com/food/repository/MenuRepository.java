
package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.food.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
