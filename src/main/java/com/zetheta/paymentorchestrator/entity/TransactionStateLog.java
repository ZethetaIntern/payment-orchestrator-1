package com.zetheta.paymentorchestrator.entity;

import com.zetheta.paymentorchestrator.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_state_log")
public class TransactionStateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auditId;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus fromState;

    @Enumerated(EnumType.STRING)
    private PaymentStatus toState;

    private String eventName;

    private String gatewayReference;

    @Column(columnDefinition = "TEXT")
    private String gatewayResponse;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    private LocalDateTime createdAt;

    private String createdBy;

    public TransactionStateLog() {
        this.auditId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getAuditId() {
        return auditId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getFromState() {
        return fromState;
    }

    public void setFromState(PaymentStatus fromState) {
        this.fromState = fromState;
    }

    public PaymentStatus getToState() {
        return toState;
    }

    public void setToState(PaymentStatus toState) {
        this.toState = toState;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getGatewayReference() {
        return gatewayReference;
    }

    public void setGatewayReference(String gatewayReference) {
        this.gatewayReference = gatewayReference;
    }

    public String getGatewayResponse() {
        return gatewayResponse;
    }

    public void setGatewayResponse(String gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}