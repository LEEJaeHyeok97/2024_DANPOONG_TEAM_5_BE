package com.jangburich.domain.store.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record StoreTeamResponseDTO(
	Long id,
	Integer point,
	Long teamId,
	Long storeId

) {

	@QueryProjection
	public StoreTeamResponseDTO(Long id, Integer point, Long teamId, Long storeId) {
		this.id = id;
		this.point = point;
		this.teamId = teamId;
		this.storeId = storeId;
	}
}
