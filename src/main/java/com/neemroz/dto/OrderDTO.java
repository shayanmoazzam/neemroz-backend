package com.neemroz.dto;

import com.neemroz.model.Order;
import com.neemroz.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {

    private Long id;
    private String orderNumber;
    private String status;          // lowercase string e.g. "pending"
    private BigDecimal totalAmount;
    private BigDecimal subtotal;
    private BigDecimal shippingCharge;
    private String paymentMethod;
    private String paymentStatus;
    private String cancelReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingState;
    private String shippingPinCode;

    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long id;
        private String name;
        private String imageUrl;
        private String category;
        private Integer quantity;
        private String selectedSize;
        private String selectedColor;
        private BigDecimal price;
        private BigDecimal totalPrice;
        private Long productId;
    }

    public static OrderDTO from(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name().toLowerCase() : "pending");
        dto.setTotalAmount(order.getTotalAmount());
        dto.setSubtotal(order.getSubtotal());
        dto.setShippingCharge(order.getShippingCharge());
        dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);
        dto.setPaymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name().toLowerCase() : null);
        dto.setCancelReason(order.getCancelReason());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setShippingName(order.getShippingName());
        dto.setShippingPhone(order.getShippingPhone());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setShippingCity(order.getShippingCity());
        dto.setShippingState(order.getShippingState());
        dto.setShippingPinCode(order.getShippingPinCode());

        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream().map(OrderDTO::mapItem).collect(Collectors.toList()));
        }
        return dto;
    }

    private static OrderItemDTO mapItem(OrderItem item) {
        OrderItemDTO d = new OrderItemDTO();
        d.setId(item.getId());
        d.setQuantity(item.getQuantity());
        d.setSelectedSize(item.getSelectedSize());
        d.setSelectedColor(item.getSelectedColor());
        d.setPrice(item.getPrice());
        d.setTotalPrice(item.getTotalPrice());
        if (item.getProduct() != null) {
            d.setProductId(item.getProduct().getId());
            d.setName(item.getProduct().getName());
            d.setImageUrl(item.getProduct().getImageUrl());
            d.setCategory(item.getProduct().getCategory());
        }
        return d;
    }
}
