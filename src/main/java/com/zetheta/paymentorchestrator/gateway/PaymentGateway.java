package com.zetheta.paymentorchestrator.gateway;

import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.enums.GatewayType;

public interface PaymentGateway {

    boolean authorize(Transaction transaction);

    boolean capture(Transaction transaction);

    GatewayType getGatewayType();
}