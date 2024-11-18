package com.jangburich.domain.payment.application;

import com.jangburich.domain.payment.application.strategy.PaymentServiceStrategy;
import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final PaymentServiceStrategy paymentServiceStrategy;

    public ReadyResponse processPayment(Long userId, PayRequest payRequest) {
        PaymentService paymentService = paymentServiceStrategy.getPaymentService(payRequest.paymentType());
        return paymentService.payReady(userId, payRequest);
    }

    public ApproveResponse processSuccess(Long userId, String tid, String pgToken) {
        PaymentService paymentService = paymentServiceStrategy.getPaymentService("kakao");
        return paymentService.payApprove(userId, tid, pgToken);
    }
}
