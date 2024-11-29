package com.jangburich.domain.store.dto.response;

import com.jangburich.domain.store.domain.Category;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record SearchStoresResponse(
	Long storeId,
	String name,
	Double latitude,
	Double longitude,
	Boolean isFavorite,
	String category,
	Double distance,
	String businessStatus,
	String closeTime,
	String phoneNumber,
	String imageUrl
) {

	@QueryProjection
	public SearchStoresResponse(Long storeId, String name, Double latitude, Double longitude, Boolean isFavorite,
		Category category, Double distance, String businessStatus, String closeTime,
		String phoneNumber, String imageUrl) {
		this(
			storeId,
			name,
			latitude,
			longitude,
			isFavorite,
			category.getDisplayName(),
			distance,
			businessStatus,
			closeTime,
			phoneNumber,
			imageUrl
		);
	}
}