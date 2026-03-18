package com.neemroz.controller;

import com.neemroz.model.CartItem;
import com.neemroz.model.User;
import com.neemroz.repository.UserRepository;
import com.neemroz.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.getCart(getUserId(userDetails)));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCartCount(
            @AuthenticationPrincipal UserDetails userDetails) {
        int count = cartService.getCartCount(getUserId(userDetails));
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddToCartRequest request) {
        CartItem item = cartService.addToCart(
                getUserId(userDetails),
                request.getProductId(),
                request.getQuantity(),
                request.getSize(),
                request.getColor()
        );
        return ResponseEntity.ok(item);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> body) {
        CartItem item = cartService.updateQuantity(
                cartItemId, getUserId(userDetails), body.get("quantity"));
        return item != null
                ? ResponseEntity.ok(item)
                : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId, getUserId(userDetails));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCart(getUserId(userDetails));
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class AddToCartRequest {
        private Long productId;
        private int quantity = 1;
        private String size;
        private String color;
    }
}
