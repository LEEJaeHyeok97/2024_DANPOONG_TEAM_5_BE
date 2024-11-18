package com.jangburich.domain.menu.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MenuCreateRequestDTO {
	private String name;
	private String description;
	private String image_url;
	private Integer price;

	public MenuCreateRequestDTO(String name, String description, String image_url, Integer price) {
		this.name = name;
		this.description = description;
		this.image_url = image_url;
		this.price = price;
	}
}
