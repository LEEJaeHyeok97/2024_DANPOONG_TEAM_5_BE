package com.jangburich.domain.payment.presentation;

import com.jangburich.domain.payment.application.PaymentProcessingService;
import com.jangburich.domain.payment.application.PaymentService;
import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ReadyResponse;
import com.jangburich.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
