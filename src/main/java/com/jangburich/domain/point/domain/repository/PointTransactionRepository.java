package com.jangburich.domain.point.domain.repository;

import com.jangburich.domain.point.domain.PointTransaction;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.StoreChargeHistoryResponse;
import com.jangburich.domain.user.domain.User;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    List<PointTransaction> findByUser(User user);

    Page<StoreChargeHistoryResponse> findAllByStore(Store store, Pageable pageable);
}