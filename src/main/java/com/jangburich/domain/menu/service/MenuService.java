package com.jangburich.domain.menu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jangburich.domain.menu.domain.Menu;
import com.jangburich.domain.menu.domain.MenuCreateRequestDTO;
import com.jangburich.domain.menu.domain.MenuResponse;
import com.jangburich.domain.menu.domain.MenuUpdateRequestDTO;
import com.jangburich.domain.menu.repository.MenuRepository;
import com.jangburich.domain.owner.domain.Owner;
import com.jangburich.domain.owner.domain.repository.OwnerRepository;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.repository.StoreRepository;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.repository.UserRepository;
import com.jangburich.global.error.DefaultNullPointerException;
import com.jangburich.global.payload.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final OwnerRepository ownerRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;

	public void registerMenu(String customOAuthUser, MenuCreateRequestDTO menuCreateRequestDTO) {
		User user = userRepository.findByProviderId(customOAuthUser)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_STORE_ID));

		menuRepository.save(Menu.create(menuCreateRequestDTO.getName(), menuCreateRequestDTO.getDescription(),
			menuCreateRequestDTO.getImage_url(), menuCreateRequestDTO.getPrice(), store));
	}

	public void updateMenu(String customOAuthUser, Long menuId, MenuUpdateRequestDTO menuUpdateRequestDTO) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));
		if (!menu.getStore().getOwner().getUser().getProviderId().equals(customOAuthUser)) {
			throw new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION);
		}
		if (menuUpdateRequestDTO.getName() != null)
			menu.setName(menuUpdateRequestDTO.getName());
		if (menuUpdateRequestDTO.getDescription() != null)
			menu.setDescription(menuUpdateRequestDTO.getDescription());
		if (menuUpdateRequestDTO.getImage_url() != null)
			menu.setImageUrl(menuUpdateRequestDTO.getImage_url());
		if (menuUpdateRequestDTO.getPrice() != null)
			menu.setPrice(menuUpdateRequestDTO.getPrice());

		menuRepository.save(menu);
	}

	@Transactional
	public void deleteMenu(String customOAuthUser, Long id) {
		Menu menu = menuRepository.findById(id)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));
		if (!menu.getStore().getOwner().getUser().getProviderId().equals(customOAuthUser)) {
			throw new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION);
		}
		menuRepository.delete(menu);
	}

	public Page<MenuResponse> getMenu(String customOAuthUser, Pageable pageable) {
		User user = userRepository.findByProviderId(customOAuthUser)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_STORE_ID));

		return menuRepository.findAllByStore(store,pageable);
	}
}
