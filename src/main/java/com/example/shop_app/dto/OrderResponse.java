package com.example.shop_app.dto;

import com.example.shop_app.domain.Order;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    
    private Long orderId;
    private Long memberId;
    private String status;
    private int totalPrice;
    private List<OrderItemResponse> orderItems;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .memberId(order.getMember().getId())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems().stream().map(OrderItemResponse::from).collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .build();
    }
}

