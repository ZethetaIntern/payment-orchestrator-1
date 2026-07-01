package com.zetheta.paymentorchestrator.gateway;

import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.enums.GatewayType;
import org.springframework.stereotype.Component;

@Component
public class RazorpayGateway implements PaymentGateway {

    @Override
    public boolean authorize(Transaction transaction) {

        System.out.println("Trying Razorpay...");

        // Simulate failure to test failover
        return false;
    }

    @Override
    public boolean capture(Transaction transaction) {

        System.out.println("Captured using Razorpay");

        return true;
    }

    @Override
    public GatewayType getGatewayType() {
        return GatewayType.RAZORPAY;
    }
}