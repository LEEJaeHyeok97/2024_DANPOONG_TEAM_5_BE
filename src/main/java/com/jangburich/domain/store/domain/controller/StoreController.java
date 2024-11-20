package com.jangburich.domain.store.domain.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.store.domain.Category;
import com.jangburich.domain.store.domain.StoreAdditionalInfoCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreCreateRequestDTO;
import com.jangburich.domain.store.domain.StoreGetResponseDTO;
import com.jangburich.domain.store.domain.StoreTeamResponseDTO;
import com.jangburich.domain.store.domain.StoreUpdateRequestDTO;
import com.jangburich.domain.store.domain.dto.condition.StoreSearchCondition;
import com.jangburich.domain.store.domain.dto.condition.StoreSearchConditionWithType;
import com.jangburich.domain.store.domain.dto.response.PaymentGroupDetailResponse;
import com.jangburich.domain.store.domain.dto.response.SearchStoresResponse;
import com.jangburich.domain.store.domain.service.StoreService;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;
import com.jangburich.utils.parser.AuthenticationParser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Store", description = "Store API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

	private final StoreService storeService;

	@Operation(summary = "카테고리 별 가게 목록 조회", description = "카테고리 별로 가게 목록을 조회합니다.")
	@GetMapping("/category")
	public ResponseCustom<Page<SearchStoresResponse>> searchByCategory(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestParam(required = false, defaultValue = "3") Integer searchRadius,
		@RequestParam(required = false, defaultValue = "ALL") Category category,
		@ModelAttribute StoreSearchCondition storeSearchCondition, Pageable pageable) {
		return ResponseCustom.OK(
			storeService.searchByCategory(authorizationHeader, searchRadius, category, storeSearchCondition, pageable));
	}

	@Operation(summary = "매장 찾기(검색)", description = "검색어와 매장 유형에 맞는 매장을 검색합니다.")
	@GetMapping("/search")
	public ResponseCustom<Page<SearchStoresResponse>> searchStores(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestParam(required = false, defaultValue = "") String keyword,
		@ModelAttribute StoreSearchConditionWithType storeSearchConditionWithType, Pageable pageable) {
		return ResponseCustom.OK(
			storeService.searchStores(authorizationHeader, keyword, storeSearchConditionWithType, pageable));
	}

	@Operation(summary = "가게 등록", description = "신규 파트너 가게를 등록합니다.")
	@PostMapping("/create")
	public ResponseCustom<Message> createStore(
		Authentication authentication,
		@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
		storeService.createStore(AuthenticationParser.parseUserId(authentication), storeCreateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@Operation(summary = "가게 추가정보 저장", description = "예약 가능 여부, 최소 선결제 금액, 선결제 사용 기간을 저장합니다.")
	@PostMapping("/create/additionalInfo")
	public ResponseCustom<Message> createAdditionalInfo(
		Authentication authentication,
		@RequestBody StoreAdditionalInfoCreateRequestDTO storeAdditionalInfoCreateRequestDTO) {
		storeService.createAdditionalInfo(AuthenticationParser.parseUserId(authentication),
			storeAdditionalInfoCreateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@Operation(summary = "가게 정보 수정", description = "가게 정보를 수정합니다.")
	@PatchMapping("/update")
	public ResponseCustom<Message> updateStore(
		Authentication authentication,
		@RequestBody StoreUpdateRequestDTO storeUpdateRequestDTO) {
		storeService.updateStore(AuthenticationParser.parseUserId(authentication), storeUpdateRequestDTO);
		return ResponseCustom.OK(Message.builder().message("success").build());
	}

	@Operation(summary = "가게 정보 조회", description = "가게 상세 정보를 조회합니다.")
	@GetMapping("")
	public ResponseCustom<StoreGetResponseDTO> getStoreInfo(
		Authentication authentication) {
		return ResponseCustom.OK(storeService.getStoreInfo(AuthenticationParser.parseUserId(authentication)));
	}

	@Operation(summary = "결제 그룹 조회", description = "장부 결제 그룹을 조회합니다.")
	@GetMapping("/payment_group")
	public ResponseCustom<Page<StoreTeamResponseDTO>> getPaymentGroup(
		Authentication authentication,
		Pageable pageable) {
		return ResponseCustom.OK(
			storeService.getPaymentGroup(AuthenticationParser.parseUserId(authentication), pageable));
	}

	@Operation(summary = "결제 그룹 상세 조회", description = "장부 결제 그룹을 상세 조회합니다.")
	@GetMapping("/payment_group/{teamId}")
	public ResponseCustom<PaymentGroupDetailResponse> getPaymentGroupDetail(
		Authentication authentication, @PathVariable Long teamId,
		Pageable pageable) {
		return ResponseCustom.OK(
			storeService.getPaymentGroupDetail(AuthenticationParser.parseUserId(authentication), teamId, pageable));
	}

	@Operation(summary = "결제 내역 조회", description = "가게에서 일어난 결제 내역을 조회합니다.")
	@GetMapping("/payment_history")
	public ResponseCustom<?> getPaymentHistory(
		Authentication authentication,
		Pageable pageable) {
		return ResponseCustom.OK(
			storeService.getPaymentHistory(AuthenticationParser.parseUserId(authentication), pageable));
	}
}