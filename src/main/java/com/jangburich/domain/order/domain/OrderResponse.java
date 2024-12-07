package com.jangburich.domain.order.domain;

import java.time.LocalDate;
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
	private LocalDate date;
	private String price;

	public OrderResponse(Long id, String userName, LocalDate date, String price) {
		this.id = id;
		this.userName = userName;
		this.date = date;
		this.price = price;
	}
}
