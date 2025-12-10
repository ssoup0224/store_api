package com.store_api.services;

import com.store_api.dtos.ErrorDto;
import com.store_api.dtos.OrderDto;
import com.store_api.entities.Order;
import com.store_api.exceptions.CartEmptyException;
import com.store_api.exceptions.CartNotFoundException;
import com.store_api.repositories.CartRepository;
import com.store_api.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckOutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    public OrderDto.CheckOutResponse checkout(OrderDto.CheckOutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new OrderDto.CheckOutResponse(order.getId());
    }


}
