package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.Order;
import com.example.ReactivePractice.repositories.OrderRepository;
import com.example.ReactivePractice.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Flux<Order> findAll() {
        return orderRepository.findAll().delayElements(Duration.ofSeconds(1));
    }

    public Mono<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Mono<Order> save(OrderRequest request) {
        return orderRepository.save(
                Order.builder()
                        .orderStatus(request.getOrderStatus())
                        .item(request.getItem())
                        .price(request.getPrice())
                        .build()
        );
    }

    public Flux<Order> findByFirstname(String item) {
        return orderRepository.findAllByItemContainingIgnoreCase(item);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id).subscribe();
    }

}