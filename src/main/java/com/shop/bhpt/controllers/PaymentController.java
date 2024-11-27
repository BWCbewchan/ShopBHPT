package com.shop.bhpt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.bhpt.dto.PaymentRequest;
import com.shop.bhpt.dto.PaymentResponse;
import com.shop.bhpt.dto.PaymentStatus;
import com.shop.bhpt.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<PaymentStatus> getPaymentStatus(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(orderId));
    }
} 