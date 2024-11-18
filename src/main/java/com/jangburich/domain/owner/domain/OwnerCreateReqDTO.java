package com.jangburich.domain.owner.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OwnerCreateReqDTO {
	private String businessName;
	private String businessRegistrationNumber;
	private String phoneNumber;
	private String name;
	private LocalDate openingDate;
}
