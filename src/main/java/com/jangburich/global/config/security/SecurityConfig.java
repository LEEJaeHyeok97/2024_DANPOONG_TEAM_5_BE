package com.jangburich.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jangburich.domain.oauth.domain.service.OAuthUserService;
import com.jangburich.utils.CustomAuthorizationRequestResolver;
import com.jangburich.utils.CustomSuccessHandler;
import com.jangburich.utils.JwtFilter;
import com.jangburich.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final OAuthUserService oauthUserService;
	private final CustomSuccessHandler customSuccessHandler;
	private final ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login")
				.authorizationEndpoint(authorization -> authorization
					.authorizationRequestResolver(
						new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization")
					))
				.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
					.userService(oauthUserService))
				.successHandler(customSuccessHandler))

			.authorizeHttpRequests(request -> request
				.requestMatchers("/", "/oauth2/**", "/login/**", "/swagger-ui/**", "/v3/api-docs/**",
					"/swagger-resources/**", "/kakaoPay/success").permitAll()  // /kakaoPay/success 경로 인증 예외 처리
				.anyRequest().authenticated()
			);

		return http.build();
	}
}