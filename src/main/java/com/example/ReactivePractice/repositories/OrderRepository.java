package com.example.ReactivePractice.repositories;


import com.example.ReactivePractice.entities.Order;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    Flux<Order> findAllByFirstnameContainingIgnoreCase(String firstname);

}