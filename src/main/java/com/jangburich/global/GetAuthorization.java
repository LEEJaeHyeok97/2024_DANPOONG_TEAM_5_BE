package com.jangburich.global;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jangburich.domain.user.domain.KakaoApiResponseDTO;

public class GetAuthorization {
	public static String getUserId(String accessToken) {
		String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<KakaoApiResponseDTO> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request,
			KakaoApiResponseDTO.class);

		return "kakao_" + response.getBody().getId();
	}
}
