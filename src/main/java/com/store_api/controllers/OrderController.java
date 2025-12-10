package com.store_api.controllers;

import com.store_api.dtos.ErrorDto;
import com.store_api.dtos.OrderDto;
import com.store_api.exceptions.OrderNotFoundException;
import com.store_api.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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

    @GetMapping("/{orderId}")
    public OrderDto.OrderInfoDto getOrder(@PathVariable Long orderId){
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(e.getMessage()));
    }
}
