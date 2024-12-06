package com.jangburich.domain.team.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;

public record IndividualStoreDetailsResponse(
        Long storeId,
        String storeName,
        boolean isLiked,
        int remainingAmount,
        int availableAmount,
        int myUsedAmount,
        String usageStartDate,
        String usageEndDate,
        List<MyPaymentHistory> myPaymentHistories
) {

    @QueryProjection
    public IndividualStoreDetailsResponse(Long storeId, String storeName, boolean isLiked, int remainingAmount,
                                          int availableAmount, int myUsedAmount, String usageStartDate,
                                          String usageEndDate,
                                          List<MyPaymentHistory> myPaymentHistories) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.isLiked = isLiked;
        this.remainingAmount = remainingAmount;
        this.availableAmount = availableAmount;
        this.myUsedAmount = myUsedAmount;
        this.usageStartDate = usageStartDate;
        this.usageEndDate = usageEndDate;
        this.myPaymentHistories = myPaymentHistories;
    }
}
