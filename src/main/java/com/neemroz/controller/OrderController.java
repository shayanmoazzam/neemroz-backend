package com.neemroz.controller;

import com.neemroz.model.Order;
import com.neemroz.repository.UserRepository;
import com.neemroz.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PlaceOrderRequest request) {

        Order order = orderService.placeOrder(
                getUserId(userDetails),
                Order.PaymentMethod.valueOf(request.getPaymentMethod()),
                request.getShippingName(),
                request.getShippingPhone(),
                request.getShippingAddress(),
                request.getShippingCity(),
                request.getShippingState(),
                request.getShippingPinCode()
        );
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.getUserOrders(getUserId(userDetails)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId, getUserId(userDetails)));
    }

    @Data
    public static class PlaceOrderRequest {
        private String paymentMethod;
        private String shippingName;
        private String shippingPhone;
        private String shippingAddress;
        private String shippingCity;
        private String shippingState;
        private String shippingPinCode;
    }
}
