package com.jangburich.domain.store.dto.response;

import java.util.List;

import com.jangburich.domain.order.domain.OrderResponse;
import com.jangburich.domain.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentGroupDetailResponse {
	private String teamName;
	private Integer point;
	private Integer remainPoint;
	private String teamLeaderName;
	private String teamLeaderPhoneNum;
	private List<OrderResponse> historyResponses;

	public static PaymentGroupDetailResponse create(String teamName, Integer point, Integer remainPoint,
		User teamLeader, List<OrderResponse> historyResponses) {
		PaymentGroupDetailResponse paymentGroupDetailResponse = new PaymentGroupDetailResponse();
		paymentGroupDetailResponse.setTeamName(teamName);
		paymentGroupDetailResponse.setPoint(point);
		paymentGroupDetailResponse.setRemainPoint(remainPoint);
		paymentGroupDetailResponse.setTeamLeaderName(teamLeader.getNickname());
		paymentGroupDetailResponse.setTeamLeaderPhoneNum(teamLeader.getPhoneNumber());
		paymentGroupDetailResponse.setHistoryResponses(historyResponses);
		return paymentGroupDetailResponse;
	}
}
