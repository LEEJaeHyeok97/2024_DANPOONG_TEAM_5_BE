package com.jangburich.domain.order.dto.request;

public record AddCartRequest(
        Long storeId,
        Long menuId,
        int quantity
) {
}