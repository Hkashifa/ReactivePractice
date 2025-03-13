package com.example.ReactivePractice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Builder
@Table("order_food_items")
public class OrderFoodItem {

    @Id
    private Long id;
    private Long orderId;
    private Long foodItemId;



}
