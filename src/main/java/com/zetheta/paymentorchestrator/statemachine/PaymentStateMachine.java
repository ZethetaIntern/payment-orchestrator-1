package com.zetheta.paymentorchestrator.statemachine;

import com.zetheta.paymentorchestrator.entity.Transaction;
import com.zetheta.paymentorchestrator.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentStateMachine {

    public void selectRoute(Transaction transaction) {

        validate(transaction, PaymentStatus.CREATED);

        transaction.setStatus(PaymentStatus.ROUTE_SELECTED);
    }

    public void initiateAuthorization(Transaction transaction) {

        validate(transaction, PaymentStatus.ROUTE_SELECTED);

        transaction.setStatus(PaymentStatus.AUTH_INITIATED);
    }

    public void authorize(Transaction transaction) {

        validate(transaction, PaymentStatus.AUTH_INITIATED);

        transaction.setStatus(PaymentStatus.AUTHORIZED);
    }

    public void authorizationFailed(Transaction transaction) {

        validate(transaction, PaymentStatus.AUTH_INITIATED);

        transaction.setStatus(PaymentStatus.AUTH_FAILED);
    }

    public void initiateCapture(Transaction transaction) {

        validate(transaction, PaymentStatus.AUTHORIZED);

        transaction.setStatus(PaymentStatus.CAPTURE_INITIATED);
    }

    public void capture(Transaction transaction) {

        validate(transaction, PaymentStatus.CAPTURE_INITIATED);

        transaction.setStatus(PaymentStatus.CAPTURED);
    }

    public void captureFailed(Transaction transaction) {

        validate(transaction, PaymentStatus.CAPTURE_INITIATED);

        transaction.setStatus(PaymentStatus.CAPTURE_FAILED);
    }

    public void settle(Transaction transaction) {

        validate(transaction, PaymentStatus.CAPTURED);

        transaction.setStatus(PaymentStatus.SETTLED);
    }

    public void initiateRefund(Transaction transaction) {

        if (transaction.getStatus() != PaymentStatus.CAPTURED &&
                transaction.getStatus() != PaymentStatus.SETTLED) {

            throw new IllegalStateException(
                    "Refund can only be initiated for CAPTURED or SETTLED payments");
        }

        transaction.setStatus(PaymentStatus.REFUND_INITIATED);
    }

    public void refund(Transaction transaction) {

        validate(transaction, PaymentStatus.REFUND_INITIATED);

        transaction.setStatus(PaymentStatus.REFUNDED);
    }

    public void refundFailed(Transaction transaction) {

        validate(transaction, PaymentStatus.REFUND_INITIATED);

        transaction.setStatus(PaymentStatus.REFUND_FAILED);
    }

    public void fail(Transaction transaction) {

        transaction.setStatus(PaymentStatus.FAILED);
    }

    private void validate(Transaction transaction, PaymentStatus expectedStatus) {

        if (transaction.getStatus() != expectedStatus) {
            throw new IllegalStateException(
                    "Expected state " + expectedStatus +
                            " but found " + transaction.getStatus());
        }
    }
}