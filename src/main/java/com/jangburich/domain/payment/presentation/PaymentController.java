package com.jangburich.domain.payment.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.payment.application.PaymentProcessingService;
import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;
import com.jangburich.domain.payment.exception.PaymentCancellationException;
import com.jangburich.domain.payment.exception.PaymentFailedException;
import com.jangburich.global.GetAuthorization;
import com.jangburich.global.payload.ResponseCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Payment", description = "Payments API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

	private final PaymentProcessingService paymentProcessingService;

	@Operation(summary = "결제 준비", description = "카카오페이 등 결제 수단을 준비한다.")
	@PostMapping("/ready")
	public ResponseCustom<ReadyResponse> payReady(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestBody PayRequest payRequest) {
		return ResponseCustom.OK(
			paymentProcessingService.processPayment(GetAuthorization.getUserId(authorizationHeader), payRequest));
	}

	@Operation(summary = "결제 성공", description = "결제 성공")
	@GetMapping("/success")
	public ResponseCustom<ApproveResponse> afterPayRequest(@RequestParam("pg_token") String pgToken) {
		return ResponseCustom.OK(
			paymentProcessingService.processSuccess(pgToken));
	}

	@Operation(summary = "결제 취소", description = "결제 진행 중에 취소되는 경우")
	@GetMapping("/cancel")
	public void cancel() {
		throw new PaymentCancellationException();
	}

	@Operation(summary = "결제 실패", description = "결제에 실패하는 경우")
	@GetMapping("/fail")
	public void fail() {
		throw new PaymentFailedException();
	}
}