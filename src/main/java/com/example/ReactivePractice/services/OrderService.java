package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.entities.Order;
import com.example.ReactivePractice.entities.OrderFoodItem;
import com.example.ReactivePractice.repositories.FoodItemRepository;
import com.example.ReactivePractice.repositories.OrderFoodItemRepository;
import com.example.ReactivePractice.repositories.OrderRepository;
import com.example.ReactivePractice.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderFoodItemRepository orderFoodItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

    public Mono<OrderRequest> findById(Long id) {

        return orderRepository.findById(id)
                .flatMap(order -> orderFoodItemRepository.findAllByOrderId(id)
                        .flatMap(orderFoodItem -> foodItemRepository.findById(orderFoodItem.getFoodItemId()))
                        .collectList() // Collect the list reactively
                        .map(foodItemsList -> OrderRequest.builder()
                                .orderStatus(order.getOrderStatus()) // Using fetched order details
                                .totalPrice(order.getTotalPrice())
                                .groupOfItem(foodItemsList)
                                .build()
                        )
                );

    }

    public Mono<Order> createOrder(OrderRequest request) {
        return orderRepository.save(
                Order.builder()
                        .orderStatus(request.getOrderStatus())
                        .totalPrice(request.getTotalPrice())
                        .build()
        ).flatMap(order ->
                Flux.fromIterable(request.getGroupOfItem())
                        .map(FoodItem::getId)
                        .flatMap(foodItemId ->
                                orderFoodItemRepository.save(
                                        OrderFoodItem.builder()
                                                .foodItemId(foodItemId)
                                                .orderId(order.getId()) // No need to block
                                                .build()
                                )
                        )
                        .then(Mono.just(order)) // Return the order after saving items
        );

    }


    public void deleteById(Long id) {
        orderRepository.deleteById(id).subscribe();
    }

    public Mono<Order> updateOrder(Long id, OrderRequest updatedOrder) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    modelMapper.map(updatedOrder, order); // Map updated fields onto existing order
                    return orderRepository.save(order); // Save and return updated order
                })
                .switchIfEmpty(Mono.empty());
    }





}