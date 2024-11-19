package com.jangburich.domain.payment.domain.repository;

import com.jangburich.domain.payment.domain.TeamChargeHistory;
import com.jangburich.domain.payment.domain.TeamChargeHistoryResponse;
import com.jangburich.domain.team.domain.Team;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamChargeHistoryRepository extends JpaRepository<TeamChargeHistory, Long> {
    Optional<TeamChargeHistory> findByTransactionId(String tid);

    Page<TeamChargeHistoryResponse> findAllByTeam(Team team, Pageable pageable);
}
