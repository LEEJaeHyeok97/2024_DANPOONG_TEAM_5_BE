package com.jangburich.domain.team.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class TeamLeader {
    private Long user_id;
    private String accountNumber;
    private String bankName;

    public TeamLeader(Long user_id, String accountNumber, String bankName) {
        this.user_id = user_id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public boolean isSameLeader(Long userId) {
        return this.user_id.equals(userId);
    }
}
