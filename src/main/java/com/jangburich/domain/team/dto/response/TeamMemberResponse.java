package com.jangburich.domain.team.dto.response;

public record TeamMemberResponse(
        Long memberId,
        String memberName,
        Boolean isMe,
        Boolean isLeader,
        String profileImgUrl
) {
}
