package com.jangburich.domain.menu.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class MenuGetResponseDTO {
	private Long id;
	private String name;
	private String description;
	private String imageUrl;
	private Integer price;
	private Long storeId;

	public MenuGetResponseDTO(Long id, String name, String description, String imageUrl, Integer price, Long storeId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
		this.storeId = storeId;
	}
}
