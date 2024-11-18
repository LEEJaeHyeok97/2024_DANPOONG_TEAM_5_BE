package com.jangburich.domain.payment.presentation;

import com.jangburich.domain.payment.application.PaymentProcessingService;
import com.jangburich.domain.payment.application.PaymentService;
import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;
import com.jangburich.domain.payment.exception.PaymentCancellationException;
import com.jangburich.domain.payment.exception.PaymentFailedException;
import com.jangburich.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "Payments API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentProcessingService paymentProcessingService;

    @Operation(summary = "결제 준비", description = "카카오페이 등 결제 수단을 준비한다.")
    @PostMapping("/ready")
    public ResponseCustom<ReadyResponse> payReady() {
        return ResponseCustom.OK(paymentProcessingService.processPayment());
    }

    @Operation(summary = "결제 성공", description = "결제 성공")
    @PostMapping("/success")
    public ResponseCustom<ApproveResponse> afterPayRequest(
            @RequestParam("tid") String tid,
            @RequestParam("pg_token") String pgToken) {
        return ResponseCustom.OK(paymentProcessingService.processSuccess(tid, pgToken));
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
