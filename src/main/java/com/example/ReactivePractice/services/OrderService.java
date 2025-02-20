package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.Order;
import com.example.ReactivePractice.enums.OrderStatus;
import com.example.ReactivePractice.repositories.OrderRepository;
import com.example.ReactivePractice.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public Flux<Order> findAll() {
        return orderRepository.findAll().delayElements(Duration.ofSeconds(1));
    }

    public Mono<Order> findById(Long id) {
        return orderRepository.findById(id)
                .onErrorReturn(new Order(id, OrderStatus.ORDER_NONE_EXISTENT, "None", 0));
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


    public void deleteById(Long id) {
        orderRepository.deleteById(id).subscribe();
    }

    public Mono<Order> updateOrder(Long id, OrderRequest updatedOrder) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    modelMapper.map(updatedOrder, order); // Map updated fields onto existing order
                    return orderRepository.save(order); // Save and return updated order
                })
                .switchIfEmpty(Mono.empty()); // Return empty Mono if order is not found
    }


    public Mono<Order> updateOrderStatus(Order order) {


        return null;
    }

    public Flux<Order> findByStatus(Long id) {
        return null;
    }

//    private Flux<Order> orderProcssingQueue(Order order) {
//
//        BlockingQueue<Order> queue = new ArrayBlockingQueue<Order>(10);
//
//        final Runnable producer = ()-> {
//            while(true)
//            {
//                queue.put(order);
//            }
//        };
//        new Thread(producer).start();
//        new Thread(producer).start();
//
//        final Runnable consumer = ()-> {
//
//            while(true){
//                Item i = queue.take();
//                process(i);
//            }
//
//        };
//
//        new Thread(consumer).start();
//        new Thread(consumer).start();
//
//        Thread.sleep(1000);
//
//
//
//    }


}