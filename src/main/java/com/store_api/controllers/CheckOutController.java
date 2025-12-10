package com.store_api.controllers;

import com.store_api.dtos.ErrorDto;
import com.store_api.dtos.OrderDto;
import com.store_api.exceptions.CartEmptyException;
import com.store_api.exceptions.CartNotFoundException;
import com.store_api.services.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService checkOutService;

    @PostMapping("")
    public OrderDto.CheckOutResponse checkout(@RequestBody OrderDto.CheckOutRequest request) {
        return checkOutService.checkout(request);
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleCartException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
    }
}
