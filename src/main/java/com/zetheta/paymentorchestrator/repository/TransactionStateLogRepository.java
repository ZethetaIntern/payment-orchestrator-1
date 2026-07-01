package com.zetheta.paymentorchestrator.repository;

import com.zetheta.paymentorchestrator.entity.TransactionStateLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionStateLogRepository
        extends JpaRepository<TransactionStateLog, Long> {

    List<TransactionStateLog> findByTransactionId(String transactionId);

}