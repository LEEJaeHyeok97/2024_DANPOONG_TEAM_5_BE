package com.jangburich.domain.store.exception;

public class OrdersNotFoundException extends RuntimeException {
	public OrdersNotFoundException() {
		super("해당 가게를 찾을 수 없습니다.");
	}
}
