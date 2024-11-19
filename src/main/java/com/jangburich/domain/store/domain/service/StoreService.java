package com.jangburich.domain.store.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jangburich.domain.oauth.domain.CustomOAuthUser;
import com.jangburich.domain.owner.domain.Owner;
import com.jangburich.domain.owner.domain.repository.OwnerRepository;
import com.jangburich.domain.payment.domain.TeamChargeHistoryResponse;
import com.jangburich.domain.payment.domain.repository.TeamChargeHistoryRepository;
import com.jangburich.domain.store.domain.Category;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.StoreAdditionalInfoCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreChargeHistoryResponse;
import com.jangburich.domain.store.domain.StoreCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreGetResponseDTO;
import com.jangburich.domain.store.domain.StoreTeam;
import com.jangburich.domain.store.domain.StoreTeamResponseDTO;
import com.jangburich.domain.store.domain.StoreUpdateRequestDTO;
import com.jangburich.domain.store.domain.dto.condition.StoreSearchCondition;
import com.jangburich.domain.store.domain.dto.condition.StoreSearchConditionWithType;
import com.jangburich.domain.store.domain.dto.response.PaymentGroupDetailResponse;
import com.jangburich.domain.store.domain.dto.response.SearchStoresResponse;
import com.jangburich.domain.store.domain.repository.StoreRepository;
import com.jangburich.domain.store.domain.repository.StoreTeamRepository;
import com.jangburich.domain.team.domain.Team;
import com.jangburich.domain.team.domain.repository.TeamRepository;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.domain.repository.UserRepository;
import com.jangburich.global.error.DefaultNullPointerException;
import com.jangburich.global.payload.ErrorCode;
import com.jangburich.utils.parser.AuthenticationParser;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;
	private final OwnerRepository ownerRepository;
	private final UserRepository userRepository;
	private final StoreTeamRepository storeTeamRepository;
	private final TeamRepository teamRepository;
	private final TeamChargeHistoryRepository teamChargeHistoryRepository;

	@Transactional
	public void createStore(CustomOAuthUser customOAuth2User, StoreCreateRequestDTO storeCreateRequestDTO) {
		User user = userRepository.findByProviderId(customOAuth2User.getUserId())
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		storeRepository.save(Store.of(owner, storeCreateRequestDTO));
	}

	@Transactional
	public void createAdditionalInfo(CustomOAuthUser customOAuthUser,
		StoreAdditionalInfoCreateRequestDTO storeAdditionalInfoCreateRequestDTO) {
		User user = userRepository.findByProviderId(customOAuthUser.getUserId())
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		store.setReservationAvailable(storeAdditionalInfoCreateRequestDTO.getReservationAvailable());
		store.setMinPrepayment(storeAdditionalInfoCreateRequestDTO.getMinPrepayment());
		store.setMaxReservation(storeAdditionalInfoCreateRequestDTO.getMaxReservation());
		store.setPrepaymentDuration(storeAdditionalInfoCreateRequestDTO.getPrepaymentDuration());

		storeRepository.save(store);
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
			store.setWorkDays(storeUpdateRequestDTO.getDayOfWeek());
		}
		if (storeUpdateRequestDTO.getOpenTime() != null) {
			store.setOpenTime(storeUpdateRequestDTO.getOpenTime());
		}
		if (storeUpdateRequestDTO.getCloseTime() != null) {
			store.setCloseTime(storeUpdateRequestDTO.getCloseTime());
		}
		return store;
	}

	public StoreGetResponseDTO getStoreInfo(CustomOAuthUser customOAuth2User) {
		User user = userRepository.findByProviderId(customOAuth2User.getUserId())
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));

		if (!store.getOwner().getUser().getProviderId().equals(customOAuth2User.getUserId())) {
			throw new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION);
		}

		return new StoreGetResponseDTO().of(store);
	}

	public Page<StoreTeamResponseDTO> getPaymentGroup(String userId, Pageable pageable) {
		User user = userRepository.findByProviderId(userId)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		return storeTeamRepository.findAllByStore(store, pageable);
	}

	public Page<SearchStoresResponse> searchByCategory(final Authentication authentication,
		final Integer searchRadius,
		final Category category,
		final StoreSearchCondition storeSearchCondition,
		final Pageable pageable) {
		String parsed = AuthenticationParser.parseUserId(authentication);
		User user = userRepository.findByProviderId(parsed)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));
		return storeRepository.findStoresByCategory(user.getUserId(), searchRadius, category, storeSearchCondition,
			pageable);
	}

	public Page<SearchStoresResponse> searchStores(final Authentication authentication, final String keyword,
		final StoreSearchConditionWithType storeSearchConditionWithType,
		final Pageable pageable) {
		String parsed = AuthenticationParser.parseUserId(authentication);
		User user = userRepository.findByProviderId(parsed)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));
		return storeRepository.findStores(user.getUserId(), keyword, storeSearchConditionWithType, pageable);
	}

	public PaymentGroupDetailResponse getPaymentGroupDetail(String userId, Long teamId, Pageable pageable) {
		User user = userRepository.findByProviderId(userId)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Team team = teamRepository.findById(teamId)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));

		User teamLeader = userRepository.findById(team.getTeamLeader().getUser_id())
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));

		StoreTeam storeTeam = storeTeamRepository.findByStoreIdAndTeamId(store.getId(), team.getId())
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_PARAMETER));

		Page<TeamChargeHistoryResponse> chargeHistoryRepositoryAllByTeam = teamChargeHistoryRepository.findAllByTeam(
			team, pageable);

		return PaymentGroupDetailResponse.create(team.getName(), storeTeam.getPoint(), storeTeam.getRemainPoint(),
			teamLeader, chargeHistoryRepositoryAllByTeam);
	}

	public Page<StoreChargeHistoryResponse> getPaymentHistory(String userId, Pageable pageable) {
		User user = userRepository.findByProviderId(userId)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Owner owner = ownerRepository.findByUser(user)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Store store = storeRepository.findByOwner(owner)
			.orElseThrow(() -> new DefaultNullPointerException(ErrorCode.INVALID_AUTHENTICATION));

		Page<StoreChargeHistoryResponse> historyResponses = teamChargeHistoryRepository.findAllByStore(store, pageable);
		return historyResponses;
	}
}
