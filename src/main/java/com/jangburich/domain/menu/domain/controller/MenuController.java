package com.jangburich.domain.menu.domain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.menu.domain.MenuCreateRequestDTO;
import com.jangburich.domain.menu.domain.MenuGetResponseDTO;
import com.jangburich.domain.menu.domain.MenuUpdateRequestDTO;
import com.jangburich.domain.menu.domain.service.MenuService;
import com.jangburich.global.GetAuthorization;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/register")
	public ResponseCustom<Message> registerMenu(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestBody MenuCreateRequestDTO menuCreateRequestDTO) {
		menuService.registerMenu(GetAuthorization.getUserId(authorizationHeader), menuCreateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@PatchMapping("/update/{id}")
	public ResponseCustom<Message> updateMenu(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader, @PathVariable Long id,
		@RequestBody MenuUpdateRequestDTO menuUpdateRequestDTO) {
		menuService.updateMenu(GetAuthorization.getUserId(authorizationHeader), id, menuUpdateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@DeleteMapping("/{id}")
	public ResponseCustom<Message> deleteMenu(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader, @PathVariable Long id) {
		menuService.deleteMenu(GetAuthorization.getUserId(authorizationHeader), id);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@GetMapping("")
	public ResponseCustom<List<MenuGetResponseDTO>> getMenu(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader) {
		List<MenuGetResponseDTO> menu = menuService.getMenu(GetAuthorization.getUserId(authorizationHeader));
		return ResponseCustom.OK(menu);
	}
}
