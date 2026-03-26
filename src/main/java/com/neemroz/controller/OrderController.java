package com.neemroz.controller;

import com.neemroz.dto.OrderDTO;
import com.neemroz.model.Order;
import com.neemroz.model.User;
import com.neemroz.repository.UserRepository;
import com.neemroz.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // ── USER: place order ──
    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(
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
        return ResponseEntity.ok(OrderDTO.from(order));
    }

    // ── USER: get own orders ──
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<OrderDTO> dtos = orderService.getUserOrders(getUserId(userDetails))
                .stream().map(OrderDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ── USER: get single order ──
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(OrderDTO.from(
                orderService.getOrderById(orderId, getUserId(userDetails))));
    }

    // ── USER: cancel own order ──
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @RequestBody Map<String, String> body) {
        try {
            String reason = body.getOrDefault("reason", "Cancelled by customer");
            Order order = orderService.cancelOrder(orderId, getUserId(userDetails), reason);
            return ResponseEntity.ok(OrderDTO.from(order));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ── ADMIN: get ALL orders ──
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> dtos = orderService.getAllOrders()
                .stream().map(OrderDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ── ADMIN: update order status ──
    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            if (status == null || status.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Status is required"));
            }
            Order order = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(OrderDTO.from(order));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
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
