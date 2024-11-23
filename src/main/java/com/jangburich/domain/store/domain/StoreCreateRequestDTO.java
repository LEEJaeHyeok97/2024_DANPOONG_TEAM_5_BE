package com.jangburich.domain.store.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.jangburich.domain.menu.domain.MenuCreateRequestDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StoreCreateRequestDTO {
	private String storeName;
	private String phoneNumber;
	private String businessName;
	private String businessRegistrationNumber;
	private LocalDate openingDate;
	private Boolean agreeMarketing;
	private Boolean agreeAdvertise;

	private String introduction;

	@Enumerated(EnumType.STRING)
	private Category category;

	// address
	private Double latitude;
	private Double longitude;
	private String address;
	private String location;

	// business hour
	private List<DayOfWeek> dayOfWeek;
	private LocalTime openTime;
	private LocalTime closeTime;

	private List<MenuCreateRequestDTO> menuCreateRequestDTOS;

	private Long minPrepayment;
	private Long prepaymentDuration;
	private Boolean reservationAvailable;
	private Long maxReservation;
}
