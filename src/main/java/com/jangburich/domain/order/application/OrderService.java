package com.jangburich.domain.order.application;

import com.jangburich.domain.menu.domain.Menu;
import com.jangburich.domain.menu.domain.repository.MenuRepository;
import com.jangburich.domain.order.domain.Cart;
import com.jangburich.domain.order.domain.repository.CartRepository;
import com.jangburich.domain.order.domain.repository.OrdersRepository;
import com.jangburich.domain.order.dto.request.AddCartRequest;
import com.jangburich.domain.store.domain.Store;
import com.jangburich.domain.store.domain.repository.StoreRepository;
import com.jangburich.domain.user.domain.User;
import com.jangburich.domain.user.domain.repository.UserRepository;
import com.jangburich.global.payload.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Message addCart(String userProviderId, AddCartRequest addCartRequest) {
        User user = userRepository.findByProviderId(userProviderId)
                .orElseThrow(() -> new NullPointerException());

        Menu menu = menuRepository.findById(addCartRequest.menuId())
                .orElseThrow(() -> new IllegalArgumentException("등록된 메뉴를 찾을 수 없습니다."));

        System.out.println("menu.getId() = " + menu.getId());
        System.out.println("user.getUserId() = " + user.getUserId());

        Optional<Cart> optionalCart = cartRepository.findByUserIdAndMenuId(user.getUserId(), menu.getId());

        Store store = storeRepository.findById(addCartRequest.storeId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 가게 id 입니다."));

        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            existingCart.updateQuantity(addCartRequest.quantity());

            return Message.builder()
                    .message("장바구니에 상품을 추가했습니다.")
                    .build();
        }

        Cart newCart = Cart.builder()
                .quantity(addCartRequest.quantity())
                .menu(menu)
                .user(user)
                .store(store)
                .orders(null)
                .build();

        cartRepository.save(newCart);

        return Message.builder()
                .message("장바구니에 상품을 추가했습니다.")
                .build();
    }
}
