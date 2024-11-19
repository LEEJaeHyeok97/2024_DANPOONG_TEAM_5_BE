package com.jangburich.domain.store.domain;

import com.jangburich.domain.team.domain.Team;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record StoreTeamResponseDTO(
	Long id,
	Integer point,
	Long teamId,
	String teamName,
	Long storeId

) {
	@QueryProjection
	public StoreTeamResponseDTO(Long id, Integer point, Long teamId, String teamName, Long storeId) {
		this.id = id;
		this.point = point;
		this.teamId = teamId;
		this.teamName = teamName;
		this.storeId = storeId;
	}
}
