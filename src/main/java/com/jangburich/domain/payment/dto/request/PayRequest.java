package com.jangburich.domain.payment.dto.request;

public record PayRequest(
        Long storeId,
        String paymentType,
        String totalAmount,
        Long teamId
) {
}