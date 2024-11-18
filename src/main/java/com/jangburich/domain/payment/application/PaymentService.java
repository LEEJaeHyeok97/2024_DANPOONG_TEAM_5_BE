package com.jangburich.domain.payment.application;

import com.jangburich.domain.payment.dto.response.ApproveResponse;
import com.jangburich.domain.payment.dto.response.ReadyResponse;

public interface PaymentService {
    String getType();
    ReadyResponse payReady();
    ApproveResponse payApprove(String tid, String pgToken);
}