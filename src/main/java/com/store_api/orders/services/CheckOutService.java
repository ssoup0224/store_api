package com.store_api.orders.services;

import com.store_api.exceptions.CartEmptyException;
import com.store_api.exceptions.CartNotFoundException;
import com.store_api.orders.repositories.OrderRepository;
import com.store_api.orders.dtos.OrderDto;
import com.store_api.orders.entities.Order;
import com.store_api.carts.repositories.CartRepository;
import com.store_api.authentication.services.AuthService;
import com.store_api.carts.services.CartService;
import lombok.AllArgsConstructor;
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
