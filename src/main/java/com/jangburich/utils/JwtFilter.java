package com.jangburich.utils;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;
import com.jangburich.domain.oauth.domain.OAuthUserDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
		// Authorization 헤더를 찾음
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// "Bearer " 이후의 토큰 부분만 추출
		String token = authorizationHeader.substring(7);

		try {
			// 토큰 소멸 시간 검증
			if (jwtUtil.isTokenExpired(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			// 토큰에서 사용자 정보 획득
			String userId = jwtUtil.getUserId(token);
			String role = jwtUtil.getRole(token);

			// OAuth2UserDTO 생성 및 설정
			OAuthUserDTO userDTO = new OAuthUserDTO();
			userDTO.setUserId(userId);
			userDTO.setRole(role);

			// CustomOAuth2User 생성
			CustomOAuthUser customOAuth2User = new CustomOAuthUser(userDTO);

			// 스프링 시큐리티 인증 토큰 생성 및 설정
			Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
				customOAuth2User.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

		} catch (Exception e) {
			// 예외 발생 시 로그 출력 및 인증 정보 제거
			SecurityContextHolder.clearContext();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}

		filterChain.doFilter(request, response);
	}

}