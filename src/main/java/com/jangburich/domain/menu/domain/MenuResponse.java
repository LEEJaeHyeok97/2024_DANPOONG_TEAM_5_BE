package com.jangburich.domain.menu.domain;

import com.querydsl.core.annotations.QueryProjection;

public record MenuResponse(
	Long id,
	Long storeId,
	String name,
	String description,
	String imageUrl,
	Integer price
) {
	@QueryProjection
	public MenuResponse(Long id, Long storeId, String name, String description, String imageUrl, Integer price) {
		this.id = id;
		this.storeId = storeId;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}
}
