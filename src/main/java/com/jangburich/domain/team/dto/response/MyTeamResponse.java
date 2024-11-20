package com.jangburich.domain.team.dto.response;

import java.util.List;

public record MyTeamResponse(
        String teamName,
        String teamType,
        Boolean isLiked,
        int peopleCount,
        Boolean isMeLeader,
        List<String> profileImageUrl
) {
}
