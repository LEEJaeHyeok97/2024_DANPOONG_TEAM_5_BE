package com.jangburich.domain.order.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangburich.domain.order.application.OrderService;
import com.jangburich.domain.order.dto.request.AddCartRequest;
import com.jangburich.domain.order.dto.request.OrderRequest;
import com.jangburich.domain.order.dto.response.CartResponse;
import com.jangburich.global.GetAuthorization;
import com.jangburich.global.payload.Message;
import com.jangburich.global.payload.ResponseCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Order API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "장바구니 담기", description = "장바구니에 물건과 수량을 담습니다.")
	@PostMapping("/carts")
	public ResponseCustom<Message> addCart(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestBody AddCartRequest addCartRequest
	) {
		return ResponseCustom.OK(orderService.addCart(GetAuthorization.getUserId(authorizationHeader), addCartRequest));
	}

	@Operation(summary = "장바구니 조회", description = "장바구니에 담은 상품을 조회합니다.")
	@GetMapping("/carts")
	public ResponseCustom<CartResponse> getCartItems(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader
	) {
		return ResponseCustom.OK(orderService.getCartItems(GetAuthorization.getUserId(authorizationHeader)));
	}

	@Operation(summary = "상품 주문", description = "상품을 주문합니다.")
	@PostMapping
	public ResponseCustom<Message> order(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@RequestBody OrderRequest orderRequest
	) {
		return ResponseCustom.OK(orderService.order(GetAuthorization.getUserId(authorizationHeader), orderRequest));
	}

	@Operation(summary = "식권 사용", description = "식권을 사용합니다.")
	public ResponseCustom<Message> useMealTicket(
		@RequestAttribute(value = "authorizationHeader") String authorizationHeader,
		@PathVariable Long orderId
	) {
		return ResponseCustom.OK(orderService.useMealTicket(GetAuthorization.getUserId(authorizationHeader), orderId));
	}
}