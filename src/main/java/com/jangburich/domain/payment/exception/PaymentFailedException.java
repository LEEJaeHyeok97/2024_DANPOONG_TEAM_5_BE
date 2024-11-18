package com.jangburich.domain.payment.exception;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException() {
        super("결제에 실패했습니다.");
    }
}
