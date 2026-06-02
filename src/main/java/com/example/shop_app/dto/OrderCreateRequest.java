package com.example.shop_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    
    private Long productId;
    private int quantity;
}
