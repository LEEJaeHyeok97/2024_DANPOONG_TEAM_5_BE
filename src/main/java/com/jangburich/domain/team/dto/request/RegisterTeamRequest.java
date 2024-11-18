package com.jangburich.domain.team.dto.request;

public record RegisterTeamRequest(
        String teamType,
        String teamName,
        String description,
        String secretCode,
        String teamLeaderAccountNumber,
        String bankName,
        int memberLimit
) {
}
