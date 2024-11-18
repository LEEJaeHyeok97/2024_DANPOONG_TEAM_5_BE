package com.jangburich.domain.store.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.StoreTeam;
import com.jangburich.domain.store.domain.StoreTeamResponseDTO;

public interface StoreTeamRepository extends JpaRepository<StoreTeam, Long> {
	Optional<StoreTeam> findByStoreIdAndTeamId(Long store_id, Long team_id);

	Page<StoreTeamResponseDTO> findAllByStore(Store store, Pageable pageable);
}