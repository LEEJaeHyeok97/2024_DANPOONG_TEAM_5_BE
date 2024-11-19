package com.jangburich.domain.team.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.team.domain.TeamLeader;
import com.jangburich.domain.team.domain.TeamType;
import com.jangburich.domain.team.domain.UserTeam;
import com.jangburich.domain.team.domain.repository.TeamRepository;
import com.jangburich.domain.team.domain.repository.UserTeamRepository;
import com.jangburich.domain.team.dto.request.RegisterTeamRequest;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.repository.UserRepository;
import com.jangburich.global.GetAuthorization;
import com.jangburich.global.payload.Message;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

	private static final int ZERO = 0;

	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final UserTeamRepository userTeamRepository;

	@Transactional
	public Message registerTeam(String userId, RegisterTeamRequest registerTeamRequest) {

		User user = userRepository.findByProviderId(GetAuthorization.getUserId(userId))
			.orElseThrow(() -> new NullPointerException());

		Team team = Team.builder()
			.name(registerTeamRequest.teamName())
			.description(registerTeamRequest.description())
			.teamLeader(new TeamLeader(user.getUserId(), registerTeamRequest.teamLeaderAccountNumber(),
				registerTeamRequest.bankName()))
			.secretCode(registerTeamRequest.secretCode())
			.point(ZERO)
			.memberLimit(registerTeamRequest.memberLimit())
			.teamType(TeamType.valueOf(registerTeamRequest.teamType()))
			.build();

		teamRepository.save(team);

		UserTeam userTeam = UserTeam.of(user, team);
		userTeamRepository.save(userTeam);

		return Message.builder()
			.message("팀 생성이 완료되었습니다.")
			.build();
	}

	@Transactional
	public Message joinTeam(String userId, String joinCode) {
		User user = userRepository.findByProviderId(GetAuthorization.getUserId(userId))
			.orElseThrow(() -> new NullPointerException());

		Team team = teamRepository.findBySecretCode(joinCode)
			.orElseThrow(() -> new IllegalArgumentException("Team not found"));

		team.validateJoinCode(joinCode);

		int currentMemberCount = userTeamRepository.countByTeam(team);
		team.validateMemberLimit(currentMemberCount);

		if (userTeamRepository.existsByUserAndTeam(user, team)) {
			throw new IllegalStateException("유저는 이미 해당 팀에 속해 있습니다.");
		}

		UserTeam userTeam = UserTeam.of(user, team);
		userTeamRepository.save(userTeam);

		return Message.builder()
			.message("팀에 성공적으로 참여하였습니다.")
			.build();
	}
}
