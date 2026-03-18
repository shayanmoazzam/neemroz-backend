package com.neemroz.service;

import com.neemroz.model.*;
import com.neemroz.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId,
                            Order.PaymentMethod paymentMethod,
                            String shippingName, String shippingPhone,
                            String shippingAddress, String shippingCity,
                            String shippingState, String shippingPinCode) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .selectedSize(cartItem.getSelectedSize())
                    .selectedColor(cartItem.getSelectedColor())
                    .price(product.getPrice())
                    .totalPrice(itemTotal)
                    .build();

            orderItems.add(orderItem);
            subtotal = subtotal.add(itemTotal);

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        BigDecimal shippingCharge = subtotal.compareTo(new BigDecimal("799")) >= 0
                ? BigDecimal.ZERO
                : new BigDecimal("49");

        BigDecimal totalAmount = subtotal.add(shippingCharge);

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .items(orderItems)
                .status(Order.OrderStatus.PENDING)
                .subtotal(subtotal)
                .shippingCharge(shippingCharge)
                .totalAmount(totalAmount)
                .paymentMethod(paymentMethod)
                .paymentStatus(Order.PaymentStatus.PENDING)
                .shippingName(shippingName)
                .shippingPhone(shippingPhone)
                .shippingAddress(shippingAddress)
                .shippingCity(shippingCity)
                .shippingState(shippingState)
                .shippingPinCode(shippingPinCode)
                .build();

        orderItems.forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteByUserId(userId);

        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Order getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }

        return order;
    }

    public void updateRazorpayOrderId(Long orderId, String razorpayOrderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setRazorpayOrderId(razorpayOrderId);
        orderRepository.save(order);
    }

    public void confirmPayment(String razorpayOrderId, String razorpayPaymentId) {
        Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setRazorpayPaymentId(razorpayPaymentId);
        order.setPaymentStatus(Order.PaymentStatus.PAID);
        order.setStatus(Order.OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = new Random().nextInt(9000) + 1000;
        return "NR" + timestamp + random;
    }
}
