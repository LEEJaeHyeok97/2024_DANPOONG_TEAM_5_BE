package com.jangburich.domain.payment.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record TeamChargeHistoryResponse(
	Long id,
	String transactionId,
	Integer paymentAmount,
	PaymentChargeStatus paymentChargeStatus
) {
	@QueryProjection
	public TeamChargeHistoryResponse(Long id, String transactionId, Integer paymentAmount,
		PaymentChargeStatus paymentChargeStatus) {
		this.id = id;
		this.transactionId = transactionId;
		this.paymentAmount = paymentAmount;
		this.paymentChargeStatus = paymentChargeStatus;
	}
}
