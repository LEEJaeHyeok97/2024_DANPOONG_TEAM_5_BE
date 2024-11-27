package com.jangburich.domain.store.dto.response;

import java.util.List;

import com.jangburich.domain.order.domain.OrderResponse;
import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentGroupDetailResponse {
	private String teamName;
	private String teamDescription;
	private Integer point;
	private Integer remainPoint;
	private String teamLeaderName;
	private String teamLeaderPhoneNum;
	private String teamLeaderProfileImageUrl;
	private List<OrderResponse> historyResponses;

	public static PaymentGroupDetailResponse create(Team team, Integer point, Integer remainPoint,
		User teamLeader, List<OrderResponse> historyResponses) {
		PaymentGroupDetailResponse paymentGroupDetailResponse = new PaymentGroupDetailResponse();
		paymentGroupDetailResponse.setTeamName(team.getName());
		paymentGroupDetailResponse.setTeamDescription(team.getDescription());
		paymentGroupDetailResponse.setPoint(point);
		paymentGroupDetailResponse.setRemainPoint(remainPoint);
		paymentGroupDetailResponse.setTeamLeaderName(teamLeader.getNickname());
		paymentGroupDetailResponse.setTeamLeaderPhoneNum(teamLeader.getPhoneNumber());
		paymentGroupDetailResponse.setTeamLeaderProfileImageUrl(teamLeader.getProfileImageUrl());
		paymentGroupDetailResponse.setHistoryResponses(historyResponses);
		return paymentGroupDetailResponse;
	}
}
