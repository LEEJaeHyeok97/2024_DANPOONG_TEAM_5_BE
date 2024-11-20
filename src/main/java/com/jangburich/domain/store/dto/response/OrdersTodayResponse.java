package com.jangburich.domain.store.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersTodayResponse {
	private Integer totalPrice;
	private List<OrdersGetResponse> ordersGetResponses;
}
