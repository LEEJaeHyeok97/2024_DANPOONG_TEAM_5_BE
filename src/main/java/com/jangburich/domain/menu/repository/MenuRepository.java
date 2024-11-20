package com.jangburich.domain.menu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jangburich.domain.menu.domain.Menu;
import com.jangburich.domain.menu.domain.MenuResponse;
import com.jangburich.domain.store.domain.Store;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Page<MenuResponse> findAllByStore(Store store, Pageable pageable);
}
