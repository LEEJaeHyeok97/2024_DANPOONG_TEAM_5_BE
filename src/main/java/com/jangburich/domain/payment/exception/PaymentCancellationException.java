package com.jangburich.domain.payment.exception;

public class PaymentCancellationException extends RuntimeException {
    public PaymentCancellationException() {
        super("결제가 취소되었습니다.");
    }
}