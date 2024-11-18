package com.jangburich.domain.store.domain;

import com.jangburich.domain.common.BaseEntity;
import com.jangburich.domain.team.domain.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreTeam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "point")
    private Integer point;

    public void updatePoint(Integer point) {
        this.point += point;
    }

    public static StoreTeam create(Team team, Store store, Integer point) {
        StoreTeam storeTeam = new StoreTeam();
        storeTeam.setTeam(team);
        storeTeam.setStore(store);
        storeTeam.setPoint(point);
        return storeTeam;
    }
}