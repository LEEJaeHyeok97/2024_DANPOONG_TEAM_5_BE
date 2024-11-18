package com.jangburich.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;

	public CustomSuccessHandler(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		CustomOAuthUser customOAuthUser = (CustomOAuthUser)authentication.getPrincipal();
		String userId = customOAuthUser.getUserId();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();

		String token = jwtUtil.createToken(userId, role); // 10 hours

		// Set the response type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Create a JSON response with the token
		String jsonResponse = String.format("{\"token\": \"%s\"}", token);

		// Write the JSON response to the output
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

}
