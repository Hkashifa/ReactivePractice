package com.example.ReactivePractice.repositories;

import com.example.ReactivePractice.entities.OrderFoodItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderFoodItemRepository extends ReactiveCrudRepository<OrderFoodItem, Long> {

    Flux<OrderFoodItem> findAllByFirstnameContainingIgnoreCase(String firstname);

    private List<Mono> findAllByOrderId(long id);
}
