package com.zetheta.paymentorchestrator.controller;

import com.zetheta.paymentorchestrator.dto.CreatePaymentRequest;
import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Transaction createPayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreatePaymentRequest request) {

        return paymentService.createPayment(idempotencyKey, request);
    }
}