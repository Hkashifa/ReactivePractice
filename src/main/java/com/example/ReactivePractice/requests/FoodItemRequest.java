package com.example.ReactivePractice.requests;

import lombok.Data;

@Data
public class FoodItemRequest {

    private String name;
    private String description;
    private Integer price;

}
