package com.example.ReactivePractice.requests;

import com.example.ReactivePractice.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderRequest {

    private OrderStatus orderStatus;
    private String item;
    private Integer price;

}
