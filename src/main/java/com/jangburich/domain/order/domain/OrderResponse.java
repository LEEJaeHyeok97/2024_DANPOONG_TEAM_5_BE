package com.jangburich.domain.order.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderResponse {
	private Long id;
	private String userName;
	private LocalDateTime date;
	private String price;

	public OrderResponse(Long id, String userName, LocalDateTime date, String price) {
		this.id = id;
		this.userName = userName;
		this.date = date;
		this.price = price;
	}
}
