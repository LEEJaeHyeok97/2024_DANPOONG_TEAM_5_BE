package com.jangburich.domain.store.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrdersDetailResponse {
	private Long id;
	private String teamName;
	private String teamUserName;
	private List<Menu> menus;
	private LocalDateTime dateTime;
	private Integer amount;
	private Integer totalPrice;
	private Integer discountPrice;

	@Getter
	@Setter
	public static class Menu {
		private String menuName;
		private Integer amount;
	}
}

