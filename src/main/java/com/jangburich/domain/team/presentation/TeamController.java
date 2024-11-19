package com.jangburich.domain.team.presentation;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.team.application.TeamService;
import com.jangburich.domain.team.dto.request.RegisterTeamRequest;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import com.jangburich.utils.parser.AuthenticationParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Team", description = "Team API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

	private final TeamService teamService;

	@Operation(summary = "팀 생성", description = "팀을 생성한다. 팀 리더는 생성자")
	@PostMapping
	public ResponseCustom<Message> registerTeam(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestBody RegisterTeamRequest registerTeamRequest
	) {
		return ResponseCustom.OK(
			teamService.registerTeam(authorizationHeader, registerTeamRequest));
	}

	@Operation(summary = "팀 가입", description = "비밀 코드를 입력하여, 팀에 가입한다.")
	@PostMapping("/join/{joinCode}")
	public ResponseCustom<Message> joinTeam(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@PathVariable("joinCode") String joinCode
	) {
		return ResponseCustom.OK(teamService.joinTeam(authorizationHeader, joinCode));
	}
}
