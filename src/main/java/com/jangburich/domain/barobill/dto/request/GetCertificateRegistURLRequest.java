package com.jangburich.domain.barobill.dto.request;

public record GetCertificateRegistURLRequest(
        String corpNum,
        String id,
        String pwd
) {
}
