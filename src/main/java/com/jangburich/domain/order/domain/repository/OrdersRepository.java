package com.jangburich.domain.order.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.jangburich.domain.order.domain.OrderStatus;
import com.jangburich.domain.order.domain.Orders;
import com.jangburich.domain.store.domain.Store;

import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	@Query(value = "SELECT * FROM orders WHERE store_id = :storeId AND updated_at < :updatedAt AND order_status = :orderStatus",
		nativeQuery = true)
	List<Orders> findOrdersByStoreAndDateAndStatusNative(
		@Param("storeId") Long storeId,
		@Param("updatedAt") LocalDateTime updatedAt,
		@Param("orderStatus") String orderStatus);

}
