package com.jangburich.domain.payment.dto.response;

public record ApproveResponse(
        String aid,
        String tid,
        String cid,
        String partner_order_id,
        String partner_user_id,
        String payment_method_type,
        String item_name,
        String item_code,
        Integer quantity,
        String created_at,
        String approved_at,
        String payload
) {
}
