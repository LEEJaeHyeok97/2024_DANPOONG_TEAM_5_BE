package com.jangburich.domain.user.domain;

import com.jangburich.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long userId;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_term_accepted")
    private Boolean isTermAccepted;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "character_image_url")
    private String characterImageUrl;

    @Column(name = "role")
    private String role;

    public static User create(String userId, String nickname, String image, String role) {
        User newUser = new User();
        newUser.setProviderId(userId);
        newUser.setNickname(nickname);
        newUser.setProfileImageUrl(image);
        newUser.setRole(role);
        return newUser;
    }
}
