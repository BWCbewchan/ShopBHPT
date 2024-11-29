package com.shop.bhpt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.dto.CheckoutRequest;
import com.shop.bhpt.entities.Order;
import com.shop.bhpt.exceptions.OrderException;
import com.shop.bhpt.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private  OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@Valid @RequestBody CheckoutRequest request) {
        try {
            return ResponseEntity.ok(orderService.createOrder(request));
        } catch (Exception e) {
            throw new OrderException("Không thể tạo đơn hàng: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders() {
        try {
            return ResponseEntity.ok(orderService.getCurrentUserOrders());
        } catch (Exception e) {
            throw new OrderException("Không thể lấy danh sách đơn hàng");
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderDetails(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            throw new OrderException("Không thể lấy chi tiết đơn hàng");
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.cancelOrder(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            throw new OrderException("Không thể hủy đơn hàng");
        }
    }
} 