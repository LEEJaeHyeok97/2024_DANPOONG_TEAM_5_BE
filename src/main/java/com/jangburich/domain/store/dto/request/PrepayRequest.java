package com.jangburich.domain.store.dto.request;

public record PrepayRequest(
        Long storeId,
        Long teamId,
        int prepayAmount,
        int personalAllocatedAmount
) {
}
