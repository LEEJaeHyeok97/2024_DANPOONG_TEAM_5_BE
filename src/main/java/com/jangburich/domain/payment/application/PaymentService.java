package com.jangburich.domain.payment.application;

import com.jangburich.domain.payment.dto.request.PayRequest;
import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;

public interface PaymentService {
    String getType();

    ReadyResponse payReady(String userId, PayRequest payRequest);

    ApproveResponse payApprove(String pgToken);
}