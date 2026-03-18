package com.neemroz.controller;

import com.neemroz.model.Order;
import com.neemroz.repository.UserRepository;
import com.neemroz.service.OrderService;
import com.neemroz.service.PaymentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreatePaymentRequest request) {

        Order order = orderService.getOrderById(request.getOrderId(), getUserId(userDetails));

        Map<String, Object> razorpayData = paymentService.createRazorpayOrder(
                order.getTotalAmount(),
                order.getOrderNumber()
        );

        orderService.updateRazorpayOrderId(order.getId(),
                (String) razorpayData.get("razorpayOrderId"));

        razorpayData.put("orderId", order.getId());
        razorpayData.put("orderNumber", order.getOrderNumber());

        return ResponseEntity.ok(razorpayData);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyPayment(
            @RequestBody VerifyPaymentRequest request) {

        boolean valid = paymentService.verifyPaymentSignature(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if (!valid) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Payment verification failed"));
        }

        orderService.confirmPayment(request.getRazorpayOrderId(), request.getRazorpayPaymentId());

        return ResponseEntity.ok(Map.of("success", true, "message", "Payment confirmed"));
    }

    @Data
    public static class CreatePaymentRequest {
        private Long orderId;
    }

    @Data
    public static class VerifyPaymentRequest {
        private String razorpayOrderId;
        private String razorpayPaymentId;
        private String razorpaySignature;
    }
}
