package com.jangburich.domain.team.domain.repository;

import com.jangburich.domain.team.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findBySecretCode(String joinCode);
}
