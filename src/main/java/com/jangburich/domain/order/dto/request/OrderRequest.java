package com.jangburich.domain.order.dto.request;

import java.util.List;

public record OrderRequest(
        Long storeId,
        Long teamId,
        List<OrderItemRequest> items
) {
    public static record OrderItemRequest(
            Long menuId,
            Integer quantity
    ) {
    }
}
