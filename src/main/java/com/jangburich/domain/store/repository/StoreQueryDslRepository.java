package com.jangburich.domain.store.repository;

import com.jangburich.domain.store.domain.Category;
import com.jangburich.domain.store.dto.condition.StoreSearchCondition;
import com.jangburich.domain.store.dto.condition.StoreSearchConditionWithType;
import com.jangburich.domain.store.dto.response.SearchStoresResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryDslRepository {
    Page<SearchStoresResponse> findStoresByCategory(Long userId, Integer searchRadius, Category category, StoreSearchCondition storeSearchCondition, Pageable pageable);

    Page<SearchStoresResponse> findStores(Long userId, String keyword, StoreSearchConditionWithType storeSearchConditionWithType, Pageable pageable);
}
