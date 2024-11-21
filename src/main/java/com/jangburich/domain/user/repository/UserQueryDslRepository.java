package com.jangburich.domain.user.repository;

import com.jangburich.domain.user.dto.response.UserHomeResponse;

public interface UserQueryDslRepository {
    UserHomeResponse findUserHomeData(Long userId);
}
