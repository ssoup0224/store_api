package com.store_api.controllers;

import com.store_api.dtos.OrderDto;
import com.store_api.entities.Order;
import com.store_api.entities.OrderItem;
import com.store_api.entities.OrderStatus;
import com.store_api.repositories.CartRepository;
import com.store_api.repositories.OrderRepository;
import com.store_api.services.AuthService;
import com.store_api.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckOutController {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping("")
    public ResponseEntity<?> checkout(@RequestBody OrderDto.CheckOutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart not found."));
        }
        if (cart.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty."));
        }

        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getCartItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return  ResponseEntity.ok(new OrderDto.CheckOutResponse(order.getId()));
    }
}
