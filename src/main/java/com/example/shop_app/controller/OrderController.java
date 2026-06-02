package com.example.shop_app.controller;

import com.example.shop_app.config.CustomUserDetails;
import com.example.shop_app.domain.Member;
import com.example.shop_app.dto.OrderCreateRequest;
import com.example.shop_app.dto.OrderResponse;
import com.example.shop_app.service.MemberService;
import com.example.shop_app.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        OrderResponse response = orderService.createOrder(request, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") Long orderId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse response = orderService.findOrderById(orderId, userDetails.getMemberId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<OrderResponse> response = orderService.getMyOrders(userDetails.getMemberId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable("orderId") Long orderId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        OrderResponse response = orderService.cancelOrder(orderId, member);
        return ResponseEntity.ok(response);
    }
}
