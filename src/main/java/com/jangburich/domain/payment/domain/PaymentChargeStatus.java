package com.jangburich.domain.payment.domain;

public enum PaymentChargeStatus {
    PENDING("충전 대기"),
    COMPLETED("충전 완료"),
    CANCELLED("충전 취소"),
    REFUNDED("충전 환불");

    private final String description;

    PaymentChargeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
