package com.jangburich.domain.payment.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("해당 팀을 찾을 수 없습니다.");
    }
}
