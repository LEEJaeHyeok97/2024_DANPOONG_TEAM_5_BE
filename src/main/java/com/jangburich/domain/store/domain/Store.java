package com.jangburich.domain.store.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jangburich.domain.owner.domain.Owner;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@Column(name = "name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private Category category;

	@Column(name = "representative_image")
	private String representativeImage;

	@Column(name = "reservation_available")
	private Boolean reservationAvailable;

	@Column(name = "max_reservation")
	private Long maxReservation;

	@Column(name = "min_prepayment")
	private Long minPrepayment;

	@Column(name = "prepayment_duration")
	private Long prepaymentDuration;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "address")
	private String address;

	@Column(name = "location")
	private String location;

	@ElementCollection(targetClass = DayOfWeek.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "work_days", joinColumns = @JoinColumn(name = "work_schedule_id"))
	@Column(name = "day_of_week")
	private List<DayOfWeek> workDays;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name = "open_time")
	private LocalTime openTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name = "close_time")
	private LocalTime closeTime;

	@Column(name = "contact_number")
	private String contactNumber;

	public static Store create(Owner owner) {
		Store newOwner = new Store();
		newOwner.setOwner(owner);
		return newOwner;
	}

	public static Store of(Owner owner, StoreCreateRequestDTO storeCreateRequestDTO) {
		Store newStore = new Store();
		newStore.setOwner(owner);
		newStore.setName(storeCreateRequestDTO.getName());
		newStore.setCategory(storeCreateRequestDTO.getCategory());
		newStore.setRepresentativeImage(storeCreateRequestDTO.getRepresentativeImage());
		newStore.setIntroduction(storeCreateRequestDTO.getIntroduction());
		newStore.setLatitude(storeCreateRequestDTO.getLatitude());
		newStore.setLongitude(storeCreateRequestDTO.getLongitude());
		newStore.setAddress(storeCreateRequestDTO.getAddress());
		newStore.setAddress(storeCreateRequestDTO.getAddress());
		newStore.setLocation(storeCreateRequestDTO.getLocation());
		newStore.setWorkDays(storeCreateRequestDTO.getDayOfWeek());
		newStore.setOpenTime(storeCreateRequestDTO.getOpenTime());
		newStore.setCloseTime(storeCreateRequestDTO.getCloseTime());
		newStore.setContactNumber(storeCreateRequestDTO.getContactNumber());
		return newStore;
	}
}
