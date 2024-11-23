package com.jangburich.domain.order.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderResponse {
	private String userName;
	private LocalDateTime date;
	private String price;

	public OrderResponse(String userName, LocalDateTime date, String price) {
		this.userName = userName;
		this.date = date;
		this.price = price;
	}
}
