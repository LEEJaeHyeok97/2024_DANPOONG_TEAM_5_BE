package com.jangburich.domain.order.domain.repository;

import com.jangburich.domain.order.dto.response.OrderResponse;

public interface OrdersQueryDslRepository {
    OrderResponse findTicket(Long ordersId);
}
