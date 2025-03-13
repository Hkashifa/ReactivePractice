package com.example.ReactivePractice.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Builder
@Table("food_items")
public class FoodItem {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer price;
}
