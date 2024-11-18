package com.jangburich.domain.store.domain.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;
import com.jangburich.domain.store.domain.StoreCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreGetResponseDTO;
import com.jangburich.domain.store.domain.StoreUpdateRequestDTO;
import com.jangburich.domain.store.domain.service.StoreService;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Store", description = "Store API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
	private final StoreService storeService;

	@Operation(summary = "가게 등록", description = "신규 파트너 가게를 등록합니다.")
	@PostMapping("/create")
	public ResponseCustom<Message> createStore(Authentication authentication,
		@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
		CustomOAuthUser customOAuth2User = (CustomOAuthUser)authentication.getPrincipal();
		storeService.CreateStore(customOAuth2User, storeCreateRequestDTO);
		return ResponseCustom.OK(Message.builder()
			.message("success")
			.build());
	}

	@Operation(summary = "가게 정보 수정", description = "가게 정보를 수정합니다.")
	@PatchMapping("/{storeId}/update")
	public ResponseCustom<Message> updateStore(Authentication authentication, @PathVariable Long storeId, @RequestBody
	StoreUpdateRequestDTO storeUpdateRequestDTO) {
		CustomOAuthUser principal = (CustomOAuthUser)authentication.getPrincipal();
		storeService.updateStore(principal, storeId, storeUpdateRequestDTO);
		return ResponseCustom.OK(Message.builder()
			.message("success")
			.build());
	}

	@Operation(summary = "가게 정보 조회", description = "가게 상세 정보를 조회합니다.")
	@GetMapping("")
	public ResponseCustom<StoreGetResponseDTO> getStoreInfo(Authentication authentication) {
		CustomOAuthUser customOAuth2User = (CustomOAuthUser)authentication.getPrincipal();
		return ResponseCustom.OK(storeService.getStoreInfo(customOAuth2User));
	}
}