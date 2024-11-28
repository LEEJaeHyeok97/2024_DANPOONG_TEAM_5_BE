package com.jangburich.domain.store.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PrepaymentInfoResponse {
	private Long minPrepayAmount;
	private Integer wallet;
	private Integer remainPrepay;
}
