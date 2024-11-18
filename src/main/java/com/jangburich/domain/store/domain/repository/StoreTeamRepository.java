package com.jangburich.domain.store.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangburich.domain.store.domain.StoreTeam;

public interface StoreTeamRepository extends JpaRepository<StoreTeam, Long> {
    Optional<StoreTeam> findByStoreIdAndTeamId(Long store_id, Long team_id);
}