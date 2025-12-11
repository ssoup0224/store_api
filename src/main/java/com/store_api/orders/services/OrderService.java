package com.store_api.orders.services;

import com.store_api.exceptions.OrderNotFoundException;
import com.store_api.mappers.OrderMapper;
import com.store_api.orders.repositories.OrderRepository;
import com.store_api.orders.dtos.OrderDto;
import com.store_api.authentication.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    public List<OrderDto.OrderInfoDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrderByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto.OrderInfoDto getOrder(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if(!order.isCustomerEqualsCurrentUser(user)){
            throw new AccessDeniedException("You dont have access to this order.");
        }
        return orderMapper.toDto(order);
    }
}
