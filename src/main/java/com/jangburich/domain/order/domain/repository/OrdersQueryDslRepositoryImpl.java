package com.jangburich.domain.order.domain.repository;

import static com.jangburich.domain.menu.domain.QMenu.menu;
import static com.jangburich.domain.order.domain.QCart.cart;
import static com.jangburich.domain.order.domain.QOrders.orders;

import com.jangburich.domain.common.Status;
import com.jangburich.domain.order.dto.response.OrderItemResponse;
import com.jangburich.domain.order.dto.response.OrderResponse;
import com.jangburich.domain.order.dto.response.QOrderItemResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrdersQueryDslRepositoryImpl implements OrdersQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public OrderResponse findTicket(Long ordersId) {

        List<OrderItemResponse> orderItemResponses = queryFactory
                .selectDistinct(new QOrderItemResponse(
                        cart.menu.name,
                        cart.quantity,
                        cart.menu.price
                ))
                .from(cart)
                .leftJoin(orders).on(cart.orders.id.eq(ordersId))
                .leftJoin(menu).on(cart.menu.id.eq(menu.id))
                .where((cart.orders.id.eq(ordersId)),
                        cart.status.eq(Status.ACTIVE))
                .fetch();

        int totalPrice = orderItemResponses.stream()
                .mapToInt(item -> item.price() * item.quantity())
                .sum();

        return new OrderResponse(ordersId, totalPrice, orderItemResponses);
    }
}
