package com.jangburich.domain.store.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record StoreMenu(
        String menuName,
        Boolean isSignatureMenu,
        String description,
        int price,
        String menuImgUrl
) {

    @QueryProjection
    public StoreMenu(String menuName, Boolean isSignatureMenu, String description, int price, String menuImgUrl) {
        this.menuName = menuName;
        this.isSignatureMenu = isSignatureMenu;
        this.description = description;
        this.price = price;
        this.menuImgUrl = menuImgUrl;
    }
}
