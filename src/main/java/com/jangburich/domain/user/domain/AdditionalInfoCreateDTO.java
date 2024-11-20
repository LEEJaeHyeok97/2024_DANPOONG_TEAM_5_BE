package com.jangburich.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdditionalInfoCreateDTO {
	private String name;
	private String phoneNum;
	private Boolean agreeMarketing;
	private Boolean agreeAdvertisement;
}
