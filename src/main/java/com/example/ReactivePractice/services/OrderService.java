package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.entities.Order;
import com.example.ReactivePractice.entities.OrderFoodItem;
import com.example.ReactivePractice.repositories.FoodItemRepository;
import com.example.ReactivePractice.repositories.OrderFoodItemRepository;
import com.example.ReactivePractice.repositories.OrderRepository;
import com.example.ReactivePractice.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderFoodItemRepository orderFoodItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    public Flux<Order> findAll() {
        return orderRepository.findAll()
                .doOnError(error -> log.error("Error fetching orders", error));
    }

    public Mono<OrderRequest> findById(Long id) {

        return orderRepository.findById(id)
                .flatMap(order -> orderFoodItemRepository.findAllByOrderId(id)
                        .flatMap(orderFoodItem -> foodItemRepository.findById(orderFoodItem.getFoodItemId()))
                        .collectList() // Collect the list reactively
                        .doOnNext(list -> {
                            if (list.isEmpty()) {
                                log.warn("No food items found for order {}", id);
                            }
                        })
                        .map(foodItemsList -> OrderRequest.builder()
                                .orderStatus(order.getOrderStatus()) // Using fetched order details
                                .totalPrice(order.getTotalPrice())
                                .groupOfItem(foodItemsList)
                                .build()
                        ).doOnError(error -> log.error("Error fetching orders", error))
                );

    }

    public Mono<Order> createOrder(OrderRequest request) {
        return orderRepository.save(
                        Order.builder()
                                .orderStatus(request.getOrderStatus())
                                .totalPrice(request.getTotalPrice())
                                .build()
                ).doOnError(error -> log.error("Error saving order", error))// Log order save failure
                .flatMap(order ->
                        Flux.fromIterable(request.getGroupOfItem())
                                .map(FoodItem::getId)
                                .flatMap(foodItemId ->
                                        orderFoodItemRepository.save(
                                                OrderFoodItem.builder()
                                                        .foodItemId(foodItemId)
                                                        .orderId(order.getId())
                                                        .build()
                                        )
                                ).doOnError(error -> log.error("Error saving order items", error)) // Log item save failures
                                .collectList()
                                .doOnNext(list -> {
                                    if (list.isEmpty()) {
                                        log.warn("No food items provided for order {}", order.getId());
                                    }
                                })
                                .then(Mono.just(order))
                );

    }


    public Mono<Void> deleteOrderById(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Order not found with id: " + id))) // Throw error if not found
                .flatMap(order -> orderRepository.delete(order)) // Delete if found
                .doOnSuccess(unused -> log.info("Successfully deleted order with id: {}", id))
                .doOnError(error -> log.error("Error deleting order with id: {}", id, error));
    }

    public Mono<Order> updateOrder(Long id, OrderRequest updatedOrder) {

        return orderRepository.findById(id)
                .doOnError(error -> log.error("Error updating order with id: {}", id, error))
                .flatMap(order -> {
                    modelMapper.map(updatedOrder, order); // Map updated fields onto existing order
                    return orderRepository.save(order); // Save and return updated order
                })
                .switchIfEmpty(Mono.empty());
    }


}