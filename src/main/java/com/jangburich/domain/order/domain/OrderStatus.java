package com.jangburich.domain.order.domain;

public enum OrderStatus {
    RECEIVED,       // 주문 접수됨, 식권 발급 완료된 상태
    TICKET_USED,    // 식권 사용 완료
    CANCELLED       // 주문 취소됨
}
