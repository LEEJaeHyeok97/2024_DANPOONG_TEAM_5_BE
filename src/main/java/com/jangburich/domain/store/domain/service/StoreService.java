package com.jangburich.domain.store.domain.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;
import com.jangburich.domain.owner.domain.Owner;
import com.jangburich.domain.owner.domain.repository.OwnerRepository;
import com.jangburich.domain.store.domain.Category;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.StoreCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreGetResponseDTO;
import com.jangburich.domain.store.domain.StoreUpdateRequestDTO;
import com.jangburich.domain.store.domain.repository.StoreRepository;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.domain.repository.UserRepository;
import com.jangburich.global.error.DefaultNullPointerException;
import com.jangburich.global.payload.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Transactional
    public void CreateStore(CustomOAuthUser customOAuth2User, StoreCreateRequestDTO storeCreateRequestDTO) {
        User user = userRepository.findByProviderId(customOAuth2User.getUserId())
                .orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

        Owner owner = ownerRepository.findByUser(user)
                .orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

        storeRepository.save(Store.of(owner, storeCreateRequestDTO));
    }

    @Transactional
    public void updateStore(CustomOAuthUser customOAuth2User, Long storeId,
                            StoreUpdateRequestDTO storeUpdateRequestDTO) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_STORE_ID));
        if (!store.getOwner().getUser().getProviderId().equals(customOAuth2User.getUserId())) {
            throw new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION);
        }

        storeRepository.save(updateStore(store, storeUpdateRequestDTO));
    }

    @Transactional
    public Store updateStore(Store store, StoreUpdateRequestDTO storeUpdateRequestDTO) {
        if (storeUpdateRequestDTO.getCategory() != null) {
            store.setCategory(storeUpdateRequestDTO.getCategory());
        }
        if (storeUpdateRequestDTO.getReservationAvailable() != null) {
            store.setReservationAvailable(storeUpdateRequestDTO.getReservationAvailable());
        }
        if (storeUpdateRequestDTO.getRepresentativeImage() != null) {
            store.setRepresentativeImage(storeUpdateRequestDTO.getRepresentativeImage());
        }
        if (storeUpdateRequestDTO.getMaxReservation() != null) {
            store.setMaxReservation(storeUpdateRequestDTO.getMaxReservation());
        }
        if (storeUpdateRequestDTO.getMinPrepayment() != null) {
            store.setMinPrepayment(storeUpdateRequestDTO.getMinPrepayment());
        }
        if (storeUpdateRequestDTO.getPrepaymentDuration() != null) {
            store.setPrepaymentDuration(storeUpdateRequestDTO.getPrepaymentDuration());
        }
        if (storeUpdateRequestDTO.getIntroduction() != null) {
            store.setIntroduction(storeUpdateRequestDTO.getIntroduction());
        }
        if (storeUpdateRequestDTO.getLatitude() != null) {
            store.setLatitude(storeUpdateRequestDTO.getLatitude());
        }
        if (storeUpdateRequestDTO.getLongitude() != null) {
            store.setLongitude(storeUpdateRequestDTO.getLongitude());
        }
        if (storeUpdateRequestDTO.getAddress() != null) {
            store.setAddress(storeUpdateRequestDTO.getAddress());
        }
        if (storeUpdateRequestDTO.getLocation() != null) {
            store.setLocation(storeUpdateRequestDTO.getLocation());
        }
        if (storeUpdateRequestDTO.getDayOfWeek() != null) {
            store.setDayOfWeek(storeUpdateRequestDTO.getDayOfWeek());
        }
        if (storeUpdateRequestDTO.getOpenTime() != null) {
            store.setOpenTime(storeUpdateRequestDTO.getOpenTime());
        }
        if (storeUpdateRequestDTO.getCloseTime() != null) {
            store.setCloseTime(storeUpdateRequestDTO.getCloseTime());
        }
        return store;
    }

    public StoreGetResponseDTO getStoreInfo(CustomOAuthUser customOAuth2User, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));

        if (!store.getOwner().getUser().getProviderId().equals(customOAuth2User.getUserId())) {
            throw new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION);
        }

        return new StoreGetResponseDTO().of(store);
    }
}