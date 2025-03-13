package com.example.ReactivePractice.repositories;


import com.example.ReactivePractice.entities.Order;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {



}