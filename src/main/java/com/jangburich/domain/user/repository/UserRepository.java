package com.jangburich.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangburich.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByProviderId(String providerId);

	Optional<User> findByRefreshToken(String token);
}
