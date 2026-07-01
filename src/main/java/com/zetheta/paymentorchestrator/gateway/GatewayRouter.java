package com.zetheta.paymentorchestrator.gateway;

import com.zetheta.paymentorchestrator.enums.GatewayType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayRouter {

    private final List<PaymentGateway> gateways;

    public GatewayRouter(List<PaymentGateway> gateways) {
        this.gateways = gateways;
    }

    public PaymentGateway selectGateway(GatewayType gatewayType) {

        return gateways.stream()
                .filter(g -> g.getGatewayType() == gatewayType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gateway not found"));
    }
}