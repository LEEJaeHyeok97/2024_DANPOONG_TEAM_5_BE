package com.jangburich.domain.store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record StoreChargeHistoryResponse(
	Long id,
	LocalDateTime createdAt,
	String teamName,
	Integer paymentAmount
) {
	@QueryProjection
	public StoreChargeHistoryResponse(Long id, LocalDateTime createdAt, String teamName, Integer paymentAmount) {
		this.id = id;
		this.createdAt = createdAt;
		this.teamName = teamName;
		this.paymentAmount = paymentAmount;
	}
}
