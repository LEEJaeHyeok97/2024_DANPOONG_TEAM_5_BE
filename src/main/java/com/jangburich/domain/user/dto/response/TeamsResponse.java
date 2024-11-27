package com.jangburich.domain.user.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record TeamsResponse(
        Long teamId,
        Long storeId,
        int dDay,
        String storeImgUrl,
        Boolean isLikedAtStore,
        String teamName,
        String storeName,
        int totalAmount,
        int currentAmount
) {

    @QueryProjection
    public TeamsResponse(Long teamId, Long storeId, int dDay, String storeImgUrl, Boolean isLikedAtStore,
                         String teamName,
                         String storeName, int totalAmount, int currentAmount) {
        this.teamId = teamId;
        this.storeId = storeId;
        this.dDay = dDay;
        this.storeImgUrl = storeImgUrl;
        this.isLikedAtStore = isLikedAtStore;
        this.teamName = teamName;
        this.storeName = storeName;
        this.totalAmount = totalAmount;
        this.currentAmount = currentAmount;
    }
}
