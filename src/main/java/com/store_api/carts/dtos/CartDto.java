package com.store_api.carts.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartDto {
    private UUID id;
    private List<CartItemDto.CartItemResponse> items = new ArrayList<>(); // null 처리
    private BigDecimal totalPrice = BigDecimal.ZERO; // null 처리
}
