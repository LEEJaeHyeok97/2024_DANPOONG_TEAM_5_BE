package com.jangburich.domain.store.repository;

import static com.jangburich.domain.store.domain.QStore.store;

import com.jangburich.domain.store.domain.Category;
import com.jangburich.domain.store.dto.condition.StoreSearchCondition;
import com.jangburich.domain.store.dto.condition.StoreSearchConditionWithType;
import com.jangburich.domain.store.dto.response.QSearchStoresResponse;
import com.jangburich.domain.store.dto.response.SearchStoresResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreQueryDslRepositoryImpl implements StoreQueryDslRepository {

    private static final int RADIUS_OF_EARTH_KM = 6371;

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchStoresResponse> findStoresByCategory(Long userId, Integer searchRadius, Category category,
                                                           StoreSearchCondition storeSearchCondition, Pageable pageable) {
        double myCurrentLat = storeSearchCondition.lat();
        double myCurrentLon = storeSearchCondition.lon();

        BooleanExpression categoryCondition = isAllCategory(category);

        List<SearchStoresResponse> results = queryFactory
                .select(new QSearchStoresResponse(store.id, store.name, Expressions.FALSE, store.category,
                        Expressions.constant(1.0), Expressions.constant("open"),
                        store.closeTime.stringValue(), store.contactNumber, store.representativeImage))
                .from(store)
                .where(
                        categoryCondition,
                        withinSearchRadius(myCurrentLat, myCurrentLon, searchRadius, store.latitude, store.longitude)
                )
                .orderBy(store.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(
                        categoryCondition,
                        withinSearchRadius(myCurrentLat, myCurrentLon, searchRadius, store.latitude, store.longitude)
                );

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<SearchStoresResponse> findStores(Long userId, String keyword,
                                                 StoreSearchConditionWithType storeSearchConditionWithType,
                                                 Pageable pageable) {
        Boolean reservable = storeSearchConditionWithType.isReservable();
        Boolean operational = storeSearchConditionWithType.isOperational();
        Boolean prepaid = storeSearchConditionWithType.isPrepaid();
        Boolean postpaid = storeSearchConditionWithType.isPostpaid();

        List<SearchStoresResponse> results = queryFactory
                .select(new QSearchStoresResponse(store.id, store.name, Expressions.FALSE, store.category,
                        Expressions.constant(1.0), Expressions.constant("open"),
                        store.closeTime.stringValue(), store.contactNumber, store.representativeImage))
                .from(store)
                .where(
                        store.reservationAvailable.eq(reservable),
                        store.name.contains(keyword)
                )
                .orderBy(store.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(
                        store.reservationAvailable.eq(reservable),
                        store.name.contains(keyword)
                );

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression isAllCategory(Category category) {
        return category == Category.ALL ? Expressions.TRUE : store.category.eq(category);
    }

    // TO DO
    private BooleanExpression isStoreCurrentlyOpen(String openTime, String closeTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String currentTimeStr = LocalTime.now().format(timeFormatter);
        LocalTime currentTime = LocalTime.parse(currentTimeStr, timeFormatter);

        LocalTime open = LocalTime.parse(openTime, timeFormatter);
        LocalTime close = LocalTime.parse(closeTime, timeFormatter);

        return Expressions.booleanTemplate("{0} >= {1} and {0} < {2}", currentTime, openTime, closeTime);
    }

    private BooleanExpression withinSearchRadius(double userLat, double userLng, int searchRadius,
                                                 com.querydsl.core.types.dsl.NumberPath<Double> storeLat,
                                                 com.querydsl.core.types.dsl.NumberPath<Double> storeLng) {
        return Expressions.numberTemplate(Double.class,
                        "({0} * acos(cos(radians({1})) * cos(radians({2})) * cos(radians({3}) - radians({4})) + sin(radians({1})) * sin(radians({2}))))",
                        RADIUS_OF_EARTH_KM, userLat, storeLat, storeLng, userLng)
                .loe(searchRadius);
    }
}
