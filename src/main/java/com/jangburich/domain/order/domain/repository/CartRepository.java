package com.jangburich.domain.order.domain.repository;

import com.jangburich.domain.common.Status;
import com.jangburich.domain.order.domain.Cart;
import com.jangburich.domain.order.domain.Orders;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId AND c.menu.id = :menuId AND c.status = :status")
    Optional<Cart> findByUserIdAndMenuIdAndStatus(@Param("userId") Long userId, @Param("menuId") Long menuId, @Param("status") Status status);

    List<Cart> findAllByUserAndStatus(User user, Status status);

    List<Cart> findAllByUserAndStoreAndStatus(User user, Store store, Status status);

    List<Cart> findAllByOrders(Orders orders);

    Optional<List<Cart>> findByUser(User user);

}
