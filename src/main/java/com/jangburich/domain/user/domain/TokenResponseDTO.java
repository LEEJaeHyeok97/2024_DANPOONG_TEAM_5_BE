package com.jangburich.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TokenResponseDTO {
	private String accessToken;
	private long accessTokenExpires;
	private String refreshToken;
	private long refreshTokenExpires;
	private Boolean alreadyExists;
}
