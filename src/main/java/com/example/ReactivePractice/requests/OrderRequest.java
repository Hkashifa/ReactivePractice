package com.example.ReactivePractice.requests;

import com.example.ReactivePractice.entities.FoodItem;
import com.example.ReactivePractice.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {

    private OrderStatus orderStatus;
    private List<FoodItem> groupOfItem;
    private Integer totalPrice;

}
