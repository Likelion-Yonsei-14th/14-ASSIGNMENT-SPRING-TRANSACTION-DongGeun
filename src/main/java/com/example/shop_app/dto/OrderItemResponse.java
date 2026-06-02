package com.example.shop_app.dto;

import com.example.shop_app.domain.OrderItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponse {
    
    private Long productId;
    private String productName;
    private int quantity;
    private int orderPrice;

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .orderPrice(orderItem.getOrderPrice())
                .build();
    }
}
