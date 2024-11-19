package com.jangburich.domain.menu.domain.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.menu.domain.MenuCreateRequestDTO;
import com.jangburich.domain.menu.domain.MenuGetResponseDTO;
import com.jangburich.domain.menu.domain.MenuUpdateRequestDTO;
import com.jangburich.domain.menu.domain.service.MenuService;
import com.jangburich.domain.oauth.domain.CustomOAuthUser;
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
	public ResponseCustom<Message> registerMenu(Authentication authentication,
		@RequestBody MenuCreateRequestDTO menuCreateRequestDTO) {
		CustomOAuthUser customOAuthUser = (CustomOAuthUser)authentication.getPrincipal();
		menuService.registerMenu(customOAuthUser, menuCreateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@PatchMapping("/update/{id}")
	public ResponseCustom<Message> updateMenu(Authentication authentication, @PathVariable Long id,
		@RequestBody MenuUpdateRequestDTO menuUpdateRequestDTO) {
		CustomOAuthUser customOAuthUser = (CustomOAuthUser)authentication.getPrincipal();
		menuService.updateMenu(customOAuthUser, id, menuUpdateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@DeleteMapping("/{id}")
	public ResponseCustom<Message> deleteMenu(Authentication authentication, @PathVariable Long id) {
		CustomOAuthUser customOAuthUser = (CustomOAuthUser)authentication.getPrincipal();
		menuService.deleteMenu(customOAuthUser, id);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@GetMapping("")
	public ResponseCustom<List<MenuGetResponseDTO>> getMenu(Authentication authentication) {
		CustomOAuthUser customOAuthUser = (CustomOAuthUser)authentication.getPrincipal();
		List<MenuGetResponseDTO> menu = menuService.getMenu(customOAuthUser);
		return ResponseCustom.OK(menu);
	}
}
