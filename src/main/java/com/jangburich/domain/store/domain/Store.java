package com.jangburich.domain.store.domain;

import com.jangburich.domain.owner.domain.Owner;
import jakarta.persistence.Column;
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

@Entity
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

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "contact_number")
    private String contactNumber;
}
