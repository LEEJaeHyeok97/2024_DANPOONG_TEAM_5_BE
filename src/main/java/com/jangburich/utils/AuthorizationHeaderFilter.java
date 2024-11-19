package com.jangburich.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthorizationHeaderFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		// Authorization 헤더를 읽어서 Request 속성으로 설정
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && !authorizationHeader.isBlank()) {
			// 속성으로 저장
			request.setAttribute("authorizationHeader", authorizationHeader);
		}

		filterChain.doFilter(request, response);
	}
}
