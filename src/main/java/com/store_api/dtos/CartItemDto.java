package com.store_api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CartItemDto {

    @Getter
    @Setter
    public static class CartItemResponse {
        private CartProductDto product;
        private Integer quantity;
        private BigDecimal totalPrice;
    }

    @Getter
    @Setter
    public static class AddItemToCartRequest {
        private Long productId;
    }

    @Getter
    @Setter
    public static class CartProductDto {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @Getter
    @Setter
    public static class UpdateCartItemRequest {
        @NotNull(message = "Quantity is required.")
        @Min(value = 1, message = "Quantity must be greater than 0.")
        private Integer quantity;
    }
}
