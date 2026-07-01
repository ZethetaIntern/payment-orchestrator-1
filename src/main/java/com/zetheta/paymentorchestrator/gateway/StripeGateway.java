package com.zetheta.paymentorchestrator.gateway;

import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.enums.GatewayType;
import org.springframework.stereotype.Component;

@Component
public class StripeGateway implements PaymentGateway {

    @Override
    public boolean authorize(Transaction transaction) {

        System.out.println("Authorized using Stripe");

        return true;
    }

    @Override
    public boolean capture(Transaction transaction) {

        System.out.println("Captured using Stripe");

        return true;
    }

    @Override
    public GatewayType getGatewayType() {
        return GatewayType.STRIPE;
    }
}