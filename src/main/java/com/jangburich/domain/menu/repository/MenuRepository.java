package com.jangburich.domain.menu.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jangburich.domain.menu.domain.Menu;
import com.jangburich.domain.menu.domain.MenuResponse;
import com.jangburich.domain.store.domain.Store;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Page<MenuResponse> findAllByStore(Store store, Pageable pageable);

    @Query("SELECT m.id, m.price FROM Menu m WHERE m.id IN :menuIds")
    List<Object[]> findPricesByMenuIds(List<Long> menuIds);
}
