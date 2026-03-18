package com.neemroz.service;

import com.neemroz.model.CartItem;
import com.neemroz.model.Product;
import com.neemroz.model.User;
import com.neemroz.repository.CartItemRepository;
import com.neemroz.repository.ProductRepository;
import com.neemroz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(Long userId, Long productId, int quantity,
                               String size, String color) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        String safeSize  = size  == null ? "" : size;
        String safeColor = color == null ? "" : color;

        Optional<CartItem> existing = cartItemRepository
                .findByUserIdAndProductIdAndSelectedSizeAndSelectedColor(
                        userId, productId, safeSize, safeColor);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        }

        CartItem item = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .selectedSize(safeSize)
                .selectedColor(safeColor)
                .build();

        return cartItemRepository.save(item);
    }

    public CartItem updateQuantity(Long cartItemId, Long userId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            return null;
        }

        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    public void removeFromCart(Long cartItemId, Long userId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }

        cartItemRepository.delete(item);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    public int getCartCount(Long userId) {
        return cartItemRepository.countByUserId(userId);
    }
}
