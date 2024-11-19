package com.jangburich.domain.user.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jangburich.domain.owner.domain.Owner;
import com.jangburich.domain.owner.domain.repository.OwnerRepository;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.repository.StoreRepository;
import com.jangburich.domain.user.domain.KakaoApiResponseDTO;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final OwnerRepository ownerRepository;
	private final StoreRepository storeRepository;

	public KakaoApiResponseDTO getUserInfo(String accessToken) {
		String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<KakaoApiResponseDTO> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request,
			KakaoApiResponseDTO.class);

		return response.getBody();
	}

	@Transactional
	public void joinUser(String accessToken) {
		KakaoApiResponseDTO userInfo = getUserInfo(accessToken);

		User user = userRepository.findByProviderId("kakao_" + userInfo.getId()).orElse(null);
		if (user == null) {
			userRepository.save(User.create("kakao_" + userInfo.getId(), userInfo.getProperties().getNickname(),
				userInfo.getKakaoAccount().getEmail(), userInfo.getProperties().getProfileImage(), "ROLE_USER"));
		}
	}

	@Transactional
	public void joinOwner(String accessToken) {
		KakaoApiResponseDTO userInfo = getUserInfo(accessToken);

		User user = userRepository.findByProviderId("kakao_" + userInfo.getId()).orElse(null);
		if (user == null) {
			User newUser = userRepository.save(
				User.create("kakao_" + userInfo.getId(), userInfo.getProperties().getNickname(),
					userInfo.getKakaoAccount().getEmail(), userInfo.getProperties().getProfileImage(), "ROLE_OWNER"));
			Owner newOwner = ownerRepository.save(Owner.create(newUser));
			storeRepository.save(Store.create(newOwner));
		}
	}

}
