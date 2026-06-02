package com.example.shop_app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // Order and OrderItem are 1:N relationship

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // Product and OrderItem are 1:N relationship

    private Integer quantity;
    private Integer orderPrice; // 주문 시점의 상품 가격 (product.getPrice() 기준)


    public static OrderItem createOrderItem(Product product, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setOrderPrice(product.getPrice());
        
        return orderItem;
    }
}