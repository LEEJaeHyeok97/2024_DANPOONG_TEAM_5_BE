package com.jangburich.domain.payment.dto.response;

public record ReadyResponse(
        String tid,
        String next_redirect_pc_url,
        String android_app_scheme,
        String ios_app_scheme
) {
}
