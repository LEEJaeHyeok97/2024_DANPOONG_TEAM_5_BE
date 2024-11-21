package com.jangburich.domain.user.dto.response;

public record PurchaseHistory(
        String date,
        Integer amount,
        String transactionTitle,
        String transactionType
) {
}
