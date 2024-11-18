package com.jangburich.domain.team.application;

import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.team.domain.TeamLeader;
import com.jangburich.domain.team.domain.TeamType;
import com.jangburich.domain.team.domain.UserTeam;
import com.jangburich.domain.team.domain.repository.TeamRepository;
import com.jangburich.domain.team.domain.repository.UserTeamRepository;
import com.jangburich.domain.team.dto.request.RegisterTeamRequest;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.domain.repository.UserRepository;
import com.jangburich.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userRepository.findByProviderId(userId)
                .orElseThrow(() -> new NullPointerException());

        Team team = Team.builder()
                .name(registerTeamRequest.teamName())
                .description(registerTeamRequest.description())
                .teamLeader(new TeamLeader(user.getUserId(), registerTeamRequest.teamLeaderAccountNumber(),
                        registerTeamRequest.bankName()))
                .memberLimit(registerTeamRequest.memberLimit())
                .teamType(TeamType.valueOf(registerTeamRequest.teamType()))
                .build();

        System.out.println("222");
        teamRepository.save(team);

        UserTeam userTeam = UserTeam.of(user, team);
        userTeamRepository.save(userTeam);

        return Message.builder()
                .message("팀 생성이 완료되었습니다.")
                .build();
    }
}
