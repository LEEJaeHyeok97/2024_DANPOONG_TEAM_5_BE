package com.jangburich.domain.order.dto.response;

public record GetCartItemsResponse(
        String menuName,
        String menuDescription,
        Integer quantity,
        Integer menuPrice
) {
    public static GetCartItemsResponse of(String menuName, String menuDescription, Integer quantity,
                                          Integer menuPrice) {
        return new GetCartItemsResponse(menuName, menuDescription, quantity, menuPrice);
    }
}
