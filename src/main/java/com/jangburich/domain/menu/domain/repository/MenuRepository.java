package com.jangburich.domain.menu.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangburich.domain.menu.domain.Menu;
import com.jangburich.domain.store.domain.Store;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByStore(Store store);
}
