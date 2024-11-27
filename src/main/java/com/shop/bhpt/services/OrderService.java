package com.shop.bhpt.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.bhpt.dto.CheckoutRequest;
import com.shop.bhpt.entities.Cart;
import com.shop.bhpt.entities.Order;
import com.shop.bhpt.entities.OrderItem;
import com.shop.bhpt.entities.User;
import com.shop.bhpt.exceptions.ResourceNotFoundException;
import com.shop.bhpt.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  OrderRepository orderRepository;
    private  CartService cartService;
    private  UserService userService;

    public Order createOrder(CheckoutRequest request) {
        User user = userService.getCurrentUser();
        Cart cart = cartService.getCurrentUserCart();

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderCode(generateOrderCode());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        order.setPhone(request.getPhone());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus("PENDING");
        order.setPaymentStatus("PENDING");

        // Convert cart items to order items
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setItem(cartItem.getItem());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setSize(cartItem.getSize());
                    orderItem.setColor(cartItem.getColor());
                    orderItem.setPrice(cartItem.getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(cart.getTotalPrice());

        // Clear cart after creating order
        cartService.clearCart();

        return orderRepository.save(order);
    }

    public List<Order> getCurrentUserOrders() {
        User user = userService.getCurrentUser();
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Order cancelOrder(Long orderId) {
        Order order = getOrderDetails(orderId);
        if (!order.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Order cannot be cancelled");
        }
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }

    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis();
    }
} 