package com.example.shop_app.service;

import com.example.shop_app.domain.Member;
import com.example.shop_app.domain.Order;
import com.example.shop_app.domain.OrderItem;
import com.example.shop_app.domain.OrderStatus;
import com.example.shop_app.domain.Product;
import com.example.shop_app.dto.OrderCreateRequest;
import com.example.shop_app.dto.OrderResponse;
import com.example.shop_app.exception.ForbiddenException;
import com.example.shop_app.exception.InvalidOrderQuantityException;
import com.example.shop_app.exception.OrderAlreadyCanceledException;
import com.example.shop_app.exception.OrderNotFoundException;
import com.example.shop_app.exception.OutOfStockException;
import com.example.shop_app.exception.ProductNotFoundException;
import com.example.shop_app.repository.OrderRepository;
import com.example.shop_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, Member member) {
        if (request == null || request.getProductId() == null) {
            throw new IllegalArgumentException("올바른 주문 정보를 입력해주세요.");
        }
        if (request.getQuantity() <= 0) {
            throw new InvalidOrderQuantityException();
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new OutOfStockException();
        }

        product.decreaseStock(request.getQuantity());

        Order order = new Order();
        order.setMember(member);

        OrderItem orderItem = OrderItem.createOrderItem(product, request.getQuantity());
        order.addOrderItem(orderItem);

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.from(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long orderId, Long memberId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getMember().getId().equals(memberId)) {
            throw new ForbiddenException();
        }

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(Long memberId) {
        return orderRepository.findByMemberId(memberId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId, Member member) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getMember().getId().equals(member.getId())) {
            throw new ForbiddenException();
        }

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderAlreadyCanceledException();
        }

        order.setStatus(OrderStatus.CANCELED);
        order.getOrderItems().forEach(item -> item.getProduct().addStock(item.getQuantity()));

        return OrderResponse.from(order);
    }
}
