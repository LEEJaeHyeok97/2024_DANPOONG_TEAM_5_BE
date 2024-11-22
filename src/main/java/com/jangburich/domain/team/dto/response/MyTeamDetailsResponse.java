package com.jangburich.domain.team.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;

public record MyTeamDetailsResponse(
        Long teamId,
        String teamName,
        String description,
        int totalPrepaidAmount,
        int remainingAmount,
        int personalRemainingAmount,
        int userUsedAmount,
        List<PrepayedStore> prepayedStores,
        List<String> teamMemberImgUrl,
        int totalMemberCount,
        List<TodayPayment> todayPayments,
        int totalTodayTransactionCount
) {

    @QueryProjection
    public MyTeamDetailsResponse(Long teamId, String teamName, String description, int totalPrepaidAmount,
                                 int remainingAmount, int personalRemainingAmount, int userUsedAmount,
                                 List<PrepayedStore> prepayedStores, List<String> teamMemberImgUrl,
                                 int totalMemberCount,
                                 List<TodayPayment> todayPayments, int totalTodayTransactionCount) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.description = description;
        this.totalPrepaidAmount = totalPrepaidAmount;
        this.remainingAmount = remainingAmount;
        this.personalRemainingAmount = personalRemainingAmount;
        this.userUsedAmount = userUsedAmount;
        this.prepayedStores = prepayedStores;
        this.teamMemberImgUrl = teamMemberImgUrl;
        this.totalMemberCount = totalMemberCount;
        this.todayPayments = todayPayments;
        this.totalTodayTransactionCount = totalTodayTransactionCount;
    }
}
