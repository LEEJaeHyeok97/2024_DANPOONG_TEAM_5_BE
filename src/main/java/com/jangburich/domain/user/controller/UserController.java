package com.jangburich.domain.user.controller;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jangburich.domain.user.domain.KakaoApiResponseDTO;
import com.jangburich.domain.user.service.UserService;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseCustom<Map<String, Object>> login(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader) {

		return ResponseCustom.OK(null);
	}

	@GetMapping("/user-info")
	public ResponseEntity<KakaoApiResponseDTO> getUserInfo(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader) {
		KakaoApiResponseDTO userInfo = userService.getUserInfo(authorizationHeader);

		return ResponseEntity.ok(userInfo);
	}

	@PostMapping("/join/user")
	public ResponseCustom<Message> joinUser(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader) {
		userService.joinUser(authorizationHeader);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@PostMapping("/join/owner")
	public ResponseCustom<Message> joinOwner(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader) {
		userService.joinOwner(authorizationHeader);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@PostMapping("/token")
	public ResponseEntity<Map<String, Object>> getAccessToken(@RequestParam("code") String code) {
		String tokenUrl = "https://kauth.kakao.com/oauth/token";

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 요청 바디 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "e155fb32e2e3f793d0ddd66212d8d461");
		params.add("redirect_uri", "http://localhost:5123");
		params.add("code", code);

		// 요청 생성
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		RestTemplate restTemplate = new RestTemplate();

		// 요청 보내기 및 응답 받기
		ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

		// 응답 반환
		return ResponseEntity.ok(response.getBody());
	}

}
