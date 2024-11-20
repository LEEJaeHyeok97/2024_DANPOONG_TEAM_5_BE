package com.jangburich.domain.team.presentation;

import com.jangburich.domain.team.dto.response.MyTeamResponse;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		Authentication authentication,
		@RequestBody RegisterTeamRequest registerTeamRequest
	) {
		return ResponseCustom.OK(
			teamService.registerTeam(AuthenticationParser.parseUserId(authentication), registerTeamRequest));
	}

	@Operation(summary = "팀 가입", description = "비밀 코드를 입력해 팀에 가입한다.")
	@PostMapping("/join/{joinCode}")
	public ResponseCustom<Message> joinTeam(
		Authentication authentication,
		@PathVariable("joinCode") String joinCode
	) {
		return ResponseCustom.OK(teamService.joinTeam(AuthenticationParser.parseUserId(authentication), joinCode));
	}

	@Operation(summary = "내가 속한 그룹 조회", description = "내가 속한 그룹을 카테고리(ALL, LEADER, MEMBER) 별로 조회한다.")
	@GetMapping
	public ResponseCustom<List<MyTeamResponse>> getMyTeamByCategory(
			Authentication authentication,
			@RequestParam(required = false, defaultValue = "ALL") String category
	) {
		return ResponseCustom.OK(teamService.getMyTeamByCategory(AuthenticationParser.parseUserId(authentication), category));
	}
}
