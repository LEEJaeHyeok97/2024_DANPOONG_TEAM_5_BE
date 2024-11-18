package com.jangburich.domain.payment.domain.repository;

import com.jangburich.domain.payment.domain.TeamChargeHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamChargeHistoryRepository extends JpaRepository<TeamChargeHistory, Long> {
    Optional<TeamChargeHistory> findByTransactionId(String tid);
}
