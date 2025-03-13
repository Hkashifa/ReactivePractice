package com.example.ReactivePractice.services;


import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.repositories.FoodItemRepository;
import com.example.ReactivePractice.requests.FoodItemRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    public Flux<FoodItem> findAll() {
        return foodItemRepository.findAll();
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
        );
    }


    public void deleteById(Long id) {
        foodItemRepository.deleteById(id).subscribe();
    }

    public Mono<FoodItem> updateFoodItem(Long id, FoodItemRequest foodItemRequest) {

        return foodItemRepository.findById(id)
                .flatMap(foodItem -> {
                    modelMapper.map(foodItemRequest, foodItem); // Map updated fields onto existing order
                    return foodItemRepository.save(foodItem); // Save and return updated order
                })
                .switchIfEmpty(Mono.empty());
    }


}
