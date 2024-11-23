package com.jangburich.domain.team.domain.repository;

import com.jangburich.domain.team.dto.response.MyTeamDetailsResponse;

public interface TeamQueryDslRepository {
    MyTeamDetailsResponse findMyTeamDetailsAsMember(Long userId, Long teamId);

    MyTeamDetailsResponse findMyTeamDetailsAsLeader(Long userId, Long teamId);
}