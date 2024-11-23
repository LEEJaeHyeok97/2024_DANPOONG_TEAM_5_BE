package com.jangburich.domain.store.dto.response;

import com.jangburich.domain.store.domain.Category;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;

public record StoreSearchDetailsResponse(
        String reservable,
        Boolean isLiked,
        Category category,
        String address,
        String operationStatus,
        String closeTime,
        String contactNumber,
        String representativeImg,
        List<StoreMenu> storeMenus
) {

    @QueryProjection
    public StoreSearchDetailsResponse(String reservable, Boolean isLiked, Category category, String address,
                                      String operationStatus, String closeTime, String contactNumber,
                                      String representativeImg, List<StoreMenu> storeMenus) {
        this.reservable = reservable;
        this.isLiked = isLiked;
        this.category = category;
        this.address = address;
        this.operationStatus = operationStatus;
        this.closeTime = closeTime;
        this.contactNumber = contactNumber;
        this.representativeImg = representativeImg;
        this.storeMenus = storeMenus;
    }
}
