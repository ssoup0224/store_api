package com.store_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CartItemDto {
    @Getter @Setter
    public static class AddItemToCartRequest {
        private Long productId;
    }

    @Getter @Setter
    public static class CartProductDto {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @Getter @Setter
    public static class CartItemDtoResponse {
        private CartProductDto product;
        private Integer quantity;
        private BigDecimal totalPrice;
    }
}
