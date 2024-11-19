package com.jangburich.domain.store.exception;

public class StoreNotFoundException extends RuntimeException{
	public StoreNotFoundException() {
		super("해당 가게를 찾을 수 없습니다.");
	}
}
