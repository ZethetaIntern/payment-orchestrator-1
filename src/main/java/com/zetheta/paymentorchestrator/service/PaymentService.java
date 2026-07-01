package com.zetheta.paymentorchestrator.service;

import com.zetheta.paymentorchestrator.dto.CreatePaymentRequest;
import com.zetheta.paymentorchestrator.entity.IdempotencyRecord;
import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.enums.GatewayType;
import com.zetheta.paymentorchestrator.enums.PaymentStatus;
import com.zetheta.paymentorchestrator.gateway.GatewayRouter;
import com.zetheta.paymentorchestrator.gateway.PaymentGateway;
import com.zetheta.paymentorchestrator.repository.IdempotencyRepository;
import com.zetheta.paymentorchestrator.repository.TransactionRepository;
import com.zetheta.paymentorchestrator.statemachine.PaymentStateMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final GatewayRouter gatewayRouter;
    private final PaymentStateMachine paymentStateMachine;
    private final AuditService auditService;

    public PaymentService(TransactionRepository transactionRepository,
                          IdempotencyRepository idempotencyRepository,
                          GatewayRouter gatewayRouter,
                          PaymentStateMachine paymentStateMachine,
                          AuditService auditService) {

        this.transactionRepository = transactionRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.gatewayRouter = gatewayRouter;
        this.paymentStateMachine = paymentStateMachine;
        this.auditService = auditService;
    }

    public Transaction createPayment(String idempotencyKey,
                                     CreatePaymentRequest request) {

        // Check idempotency
        var existingRecord =
                idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (existingRecord.isPresent()) {

            return transactionRepository
                    .findByTransactionId(existingRecord.get().getTransactionId())
                    .orElseThrow(() ->
                            new RuntimeException("Transaction not found"));
        }

        // Create transaction
        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setStatus(PaymentStatus.CREATED);

        // State transitions
        paymentStateMachine.selectRoute(transaction);
        paymentStateMachine.initiateAuthorization(transaction);

        // Select Razorpay first
        PaymentGateway gateway =
                gatewayRouter.selectGateway(GatewayType.RAZORPAY);

        boolean authorized = gateway.authorize(transaction);

        // Failover to Stripe
        if (!authorized) {

            System.out.println("Razorpay failed. Switching to Stripe...");

            gateway = gatewayRouter.selectGateway(GatewayType.STRIPE);

            authorized = gateway.authorize(transaction);
        }

        // Final authorization result
        if (authorized) {

            PaymentStatus previousState = transaction.getStatus();

            paymentStateMachine.authorize(transaction);

            auditService.logStateChange(
                    transaction,
                    previousState,
                    transaction.getStatus(),
                    "AUTH_SUCCESS"
            );

        } else {

            PaymentStatus previousState = transaction.getStatus();

            paymentStateMachine.authorizationFailed(transaction);

            auditService.logStateChange(
                    transaction,
                    previousState,
                    transaction.getStatus(),
                    "AUTH_FAILED"
            );

            throw new RuntimeException("Payment authorization failed.");
        }

        // Save transaction
        Transaction savedTransaction =
                transactionRepository.save(transaction);

        // Save idempotency record
        IdempotencyRecord record = new IdempotencyRecord();

        record.setIdempotencyKey(idempotencyKey);
        record.setTransactionId(savedTransaction.getTransactionId());

        idempotencyRepository.save(record);

        return savedTransaction;
    }
}