package com.example.ReactivePractice.repositories;

import com.example.ReactivePractice.entities.FoodItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FoodItemRepository extends ReactiveCrudRepository<FoodItem, Long> {
}
