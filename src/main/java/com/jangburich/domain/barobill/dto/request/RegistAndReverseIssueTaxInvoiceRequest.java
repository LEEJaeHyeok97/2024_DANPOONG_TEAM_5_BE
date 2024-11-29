package com.jangburich.domain.barobill.dto.request;

public record RegistAndReverseIssueTaxInvoiceRequest(
    String invoicerBarobillID,
    String invoiceeBarobillID
) {

}
