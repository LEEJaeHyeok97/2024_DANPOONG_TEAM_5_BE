package com.jangburich.domain.team.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record MyPaymentHistory(
        String paymentDate,
        String paymentTime,
        String menuName,
        int price,
        String userName
) {

    @QueryProjection
    public MyPaymentHistory(String paymentDate, String paymentTime, String menuName, int price, String userName) {
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.menuName = menuName;
        this.price = price;
        this.userName = userName;
    }
}
