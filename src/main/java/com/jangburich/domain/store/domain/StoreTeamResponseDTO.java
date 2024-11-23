package com.jangburich.domain.store.domain;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record StoreTeamResponseDTO(
	Long id,
	Integer remainPoint,
	Long teamId,
	String teamName,
	String teamDescription,
	Long storeId,
	LocalDateTime updatedAt

) {
	@QueryProjection
	public StoreTeamResponseDTO(Long id, Integer remainPoint, Long teamId, String teamName, String teamDescription,
		Long storeId, LocalDateTime updatedAt) {
		this.id = id;
		this.remainPoint = remainPoint;
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamDescription = teamDescription;
		this.storeId = storeId;
		this.updatedAt = updatedAt;
	}
}
