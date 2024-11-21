package com.jangburich.domain.user.dto.response;

import java.util.List;

public record WalletResponse(
        Integer point,
        List<PurchaseHistory> purchaseHistories
) {
}
