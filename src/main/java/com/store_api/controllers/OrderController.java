package com.store_api.controllers;

import com.store_api.dtos.OrderDto;
import com.store_api.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public List<OrderDto.OrderInfoDto> getAllOrders(){
        return orderService.getAllOrders();
    }
}
