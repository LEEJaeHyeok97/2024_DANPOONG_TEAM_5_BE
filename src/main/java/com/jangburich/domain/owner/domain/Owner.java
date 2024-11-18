package com.jangburich.domain.owner.domain;

import com.jangburich.domain.common.BaseEntity;
import com.jangburich.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

	public static Owner create(User user) {
		Owner newOwner = new Owner();
		newOwner.setUser(user);
		return newOwner;
	}

}
