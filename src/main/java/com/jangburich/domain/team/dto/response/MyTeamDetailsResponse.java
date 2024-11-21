package com.jangburich.domain.team.dto.response;

public record MyTeamDetailsResponse(
        Long teamId,
        String teamName,
        String description
) {
}
