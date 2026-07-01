package com.zetheta.paymentorchestrator.service;

import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.entity.TransactionStateLog;
import com.zetheta.paymentorchestrator.enums.PaymentStatus;
import com.zetheta.paymentorchestrator.repository.TransactionStateLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final TransactionStateLogRepository logRepository;

    public AuditService(TransactionStateLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logStateChange(Transaction transaction,
                               PaymentStatus fromState,
                               PaymentStatus toState,
                               String eventName) {

        TransactionStateLog log = new TransactionStateLog();

        log.setTransactionId(transaction.getTransactionId());
        log.setFromState(fromState);
        log.setToState(toState);
        log.setEventName(eventName);

        log.setGatewayReference("N/A");
        log.setGatewayResponse("Success");
        log.setMetadata("{}");
        log.setCreatedBy("SYSTEM");

        logRepository.save(log);
    }
}