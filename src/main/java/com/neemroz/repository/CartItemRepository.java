package com.neemroz.repository;

import com.neemroz.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
    Optional<CartItem> findByUserIdAndProductIdAndSelectedSizeAndSelectedColor(
        Long userId, Long productId, String size, String color);
    void deleteByUserId(Long userId);
    int countByUserId(Long userId);
}
