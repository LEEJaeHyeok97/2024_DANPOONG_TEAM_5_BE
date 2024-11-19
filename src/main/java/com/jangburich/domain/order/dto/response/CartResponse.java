package com.jangburich.domain.order.dto.response;

import java.util.List;

public record CartResponse(
        List<GetCartItemsResponse> cartItems,
        Integer totalAmount,
        Integer discountAmount,
        Integer finalAmount
) {
    public static CartResponse of(List<GetCartItemsResponse> cartItems, Integer discountAmount) {
        int totalAmount = cartItems.stream()
                .mapToInt(item -> item.menuPrice() * item.quantity())
                .sum();

        discountAmount = 0;

        int finalAmount = totalAmount - discountAmount;

        return new CartResponse(cartItems, totalAmount, discountAmount, finalAmount);
    }

}
