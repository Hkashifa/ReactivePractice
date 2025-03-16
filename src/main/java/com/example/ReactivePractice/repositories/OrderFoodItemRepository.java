package com.example.ReactivePractice.repositories;

import com.example.ReactivePractice.entities.OrderFoodItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderFoodItemRepository extends ReactiveCrudRepository<OrderFoodItem, Long> {

    Flux<OrderFoodItem> findAllByOrderId(Long orderId);


}
