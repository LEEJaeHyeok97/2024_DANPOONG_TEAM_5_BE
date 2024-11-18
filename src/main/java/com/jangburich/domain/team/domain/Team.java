package com.jangburich.domain.team.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.jangburich.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "secret_code")
    private String secretCode;

    @Embedded
    private TeamLeader teamLeader;

    @Column(name = "point")
    private Integer point;

    @Column(name = "member_limit")
    private Integer memberLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type")
    private TeamType teamType;

    public void updatePoint(Integer point) {
        this.point += point;
    }

    public void validateJoinCode(String joinCode) {
        if (!this.secretCode.equals(joinCode)) {
            throw new IllegalArgumentException("유효하지 않은 입장 코드입니다.");
        }
    }

    public void validateMemberLimit(int currentMemberCount) {
        if (currentMemberCount >= this.memberLimit) {
            throw new IllegalStateException("멤버 제한을 초과합니다.");
        }
    }


    @Builder
    public Team(String name, String description, String secretCode, TeamLeader teamLeader, Integer point,
                Integer memberLimit, TeamType teamType) {
        this.name = name;
        this.description = description;
        this.secretCode = secretCode;
        this.teamLeader = teamLeader;
        this.point = point;
        this.memberLimit = memberLimit;
        this.teamType = teamType;
    }
}
