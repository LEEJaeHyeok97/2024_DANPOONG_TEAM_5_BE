package com.jangburich.domain.team.domain;

import java.util.HashSet;
import java.util.Set;

import com.jangburich.domain.common.BaseEntity;
import com.jangburich.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
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

    @Column(name = "point")
    private Integer point;

    @Column(name = "member_limit")
    private Integer memberLimit;

    @ManyToMany(mappedBy = "teams")
    private Set<User> users = new HashSet<>();

    public void updatePoint(Integer point) {
        this.point += point;
    }
}
