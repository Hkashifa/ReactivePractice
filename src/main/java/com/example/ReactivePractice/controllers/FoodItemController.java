package com.example.ReactivePractice.controllers;


import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.requests.FoodItemRequest;
import com.example.ReactivePractice.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping()
    public Flux<FoodItem> getAllFoodItems() {
        return foodItemService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<FoodItem> getFoodItemById(@PathVariable Long id) {
        return foodItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FoodItem> createFoodItem(@RequestBody FoodItemRequest request) {
        return foodItemService.createFoodItem(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<FoodItem> updateFoodItem(@PathVariable Long id, @RequestBody FoodItemRequest foodItemRequest) {
        return foodItemService.updateFoodItem(id, foodItemRequest);
    }


    @DeleteMapping("/{id}")
    public void deleteFoodItemById(@PathVariable Long id) {
        foodItemService.deleteById(id);
    }


}
