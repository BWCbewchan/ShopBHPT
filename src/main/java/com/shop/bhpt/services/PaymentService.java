package com.shop.bhpt.services;

import org.springframework.stereotype.Service;

import com.shop.bhpt.dto.PaymentRequest;
import com.shop.bhpt.dto.PaymentResponse;
import com.shop.bhpt.dto.PaymentStatus;
import com.shop.bhpt.entities.Order;
import com.shop.bhpt.exceptions.ResourceNotFoundException;
import com.shop.bhpt.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private OrderRepository orderRepository;

    public PaymentResponse processPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Implement payment processing logic here
        // This is just a mock implementation
        PaymentResponse response = new PaymentResponse();
        response.setOrderId(order.getId());
        response.setStatus("SUCCESS");
        response.setTransactionId(generateTransactionId());

        // Update order payment status
        order.setPaymentStatus("COMPLETED");
        order.setStatus("PAID");
        orderRepository.save(order);

        return response;
    }

    public PaymentStatus getPaymentStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        PaymentStatus status = new PaymentStatus();
        status.setOrderId(orderId);
        status.setStatus(order.getPaymentStatus());
        return status;
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }
} 