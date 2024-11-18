package com.jangburich.domain.payment.domain;

import com.jangburich.domain.common.BaseEntity;
import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamChargeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_charge_status")
    private PaymentChargeStatus paymentChargeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public TeamChargeHistory(String transactionId, Integer paymentAmount, PaymentChargeStatus paymentChargeStatus,
                             Team team) {
        this.transactionId = transactionId;
        this.paymentAmount = paymentAmount;
        this.paymentChargeStatus = paymentChargeStatus;
        this.team = team;
    }

    public void completePaymentChargeStatus() {
        this.paymentChargeStatus = PaymentChargeStatus.COMPLETED;
    }
}
