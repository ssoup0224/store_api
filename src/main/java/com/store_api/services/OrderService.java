package com.store_api.services;

import com.store_api.dtos.OrderDto;
import com.store_api.mappers.OrderMapper;
import com.store_api.repositories.OrderRepository;
import lombok.AllArgsConstructor;
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
        var orders = orderRepository.findAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
