package com.jangburich.domain.order.domain.repository;

import com.jangburich.domain.order.domain.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId AND c.menu.id = :menuId")
    Optional<Cart> findByUserIdAndMenuId(@Param("userId") Long userId, @Param("menuId") Long menuId);
}
