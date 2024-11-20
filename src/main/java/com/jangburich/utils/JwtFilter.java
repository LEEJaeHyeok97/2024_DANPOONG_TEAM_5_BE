package com.jangburich.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${test.jwt.permanentUserToken}")
	private String permanentUserToken;

	@Value("${test.jwt.permanentOwnerToken}")
	private String permanentOwnerToken;

	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 추출

		try {
			if (token.equals(permanentUserToken)) {
				log.info("Permanent user token detected. Skipping validation.");
				setPermanentAuthentication("test-user", "ROLE_USER");
				filterChain.doFilter(request, response);
				return;
			}

			if (token.equals(permanentOwnerToken)) {
				log.info("Permanent owner token detected. Skipping validation.");
				setPermanentAuthentication("test-owner", "ROLE_OWNER");
				filterChain.doFilter(request, response);
				return;
			}

			// 일반 토큰 처리
			if (jwtUtil.isTokenExpired(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			String userId = jwtUtil.getUserId(token);
			String role = jwtUtil.getRole(token);

			OAuthUserDTO userDTO = new OAuthUserDTO();
			userDTO.setUserId(userId);
			userDTO.setRole(role);

			CustomOAuthUser customOAuth2User = new CustomOAuthUser(userDTO);

			Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
				customOAuth2User.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}

		filterChain.doFilter(request, response);
	}

	// 고정 토큰용 인증 설정
	private void setPermanentAuthentication(String userId, String role) {
		OAuthUserDTO userDTO = new OAuthUserDTO();
		userDTO.setUserId(userId);
		userDTO.setRole(role);

		CustomOAuthUser customOAuth2User = new CustomOAuthUser(userDTO);

		Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
			customOAuth2User.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}