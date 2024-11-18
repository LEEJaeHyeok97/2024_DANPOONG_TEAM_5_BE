package com.jangburich.domain.store.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StoreCreateRequestDTO {
	// detail
	private String name;

	@Enumerated(EnumType.STRING)
	private Category category;

	private String representativeImage;
	private Boolean reservationAvailable;
	private Long maxReservation;
	private Long minPrepayment;
	private Long prepaymentDuration;
	private String introduction;
	private String contactNumber;

	// address
	private Double latitude;
	private Double longitude;
	private String address;
	private String location;

	// business hour
	private String dayOfWeek;
	private String openTime;
	private String closeTime;
}
