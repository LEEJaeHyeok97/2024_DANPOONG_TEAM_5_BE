package com.jangburich.domain.payment.dto.request;

public record PayRequest(
        String paymentType,
        String totalAmount,
        Long teamId
) {
}
