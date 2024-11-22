package com.jangburich.domain.menu.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class MenuCreateRequestDTO {
	private String name;
	private String description;
	private Integer price;

	public MenuCreateRequestDTO(String name, String description, Integer price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
}
