package com.jangburich.domain.team.dto.response;

import java.time.LocalDate;
import java.util.List;

public record MyTeamResponse(
        String teamName,
        LocalDate createdDate,
        String teamType,
        Boolean isLiked,
        int peopleCount,
        Boolean isMeLeader,
        List<String> profileImageUrl
) {
}
