package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.repositories.FoodItemRepository;
import com.example.ReactivePractice.requests.FoodItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    public Flux<FoodItem> findAll() {
        return foodItemRepository.findAll()
                .doOnError(error -> log.error("Error fetching food items", error));
    }

    public Mono<FoodItem> findById(Long id) {
        return foodItemRepository.findById(id)
                .onErrorReturn(new FoodItem(id,
                        "Food Name does not exist",
                        "Food Description does not exist",
                        0));

    }

    public Mono<FoodItem> createFoodItem(FoodItemRequest foodItemRequest) {

        return foodItemRepository.save(
                FoodItem.builder()
                        .name(foodItemRequest.getName())
                        .description(foodItemRequest.getDescription())
                        .price(foodItemRequest.getPrice())
                        .build()
        ).doOnError(error -> log.error("Error creating food item", error))
                .doOnSuccess(unused->log.info("Food item created"));
    }


    public Mono<Void> deleteById(Long id) {
        return foodItemRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Food item not found with id: " + id))) // Throw error if not found
                .flatMap(fooditem -> foodItemRepository.delete(fooditem)) // Delete if found
                .doOnSuccess(unused -> log.info("Successfully deleted food item with id: {}", id))
                .doOnError(error -> log.error("Error deleting food item with id: {}", id, error));

    }

    public Mono<FoodItem> updateFoodItem(Long id, FoodItemRequest foodItemRequest) {

        return foodItemRepository.findById(id)
                .doOnError(error -> log.error("Error updating food item with id: {}", id, error))
                .flatMap(foodItem -> {
                    modelMapper.map(foodItemRequest, foodItem); // Map updated fields onto existing order
                    return foodItemRepository.save(foodItem); // Save and return updated order
                })
                .switchIfEmpty(Mono.empty());
    }


}
