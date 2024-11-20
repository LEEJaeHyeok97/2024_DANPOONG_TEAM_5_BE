package com.jangburich.domain.store.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrdersGetResponse {
	private Long id;
	private String menuNames;
	private LocalDateTime date;
	private Integer count;
	private Integer price;
}
