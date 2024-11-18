package com.jangburich.domain.payment.application.strategy;

import com.jangburich.domain.payment.application.PaymentService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceStrategy {

    private final Map<String, PaymentService> paymentServiceMap;

    public PaymentServiceStrategy(List<PaymentService> paymentServices) {
        this.paymentServiceMap = paymentServices.stream()
                .collect(Collectors.toMap(PaymentService::getType, service -> service));
    }

    public PaymentService getPaymentService(String type) {
        PaymentService paymentService = paymentServiceMap.get(type);
        if(paymentService == null) {
            throw new IllegalArgumentException("알 수 없는 결제 타입입니다: " + type);
        }
        return paymentService;
    }
}
