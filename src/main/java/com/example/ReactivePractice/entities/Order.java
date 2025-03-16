package com.example.ReactivePractice.entities;

import com.example.ReactivePractice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@Table("orders")
public class Order {

    @Id
    private Long id;
    private OrderStatus orderStatus;
    private Integer totalPrice;

}
