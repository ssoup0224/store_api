package com.store_api.orders.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public class OrderItemDto {
    @Getter
    @Setter
    public static class OrderProductDto {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @Getter
    @Setter
    public static class OrderItemResponse {
        private OrderProductDto product;
        private Integer quantity;
        private BigDecimal totalPrice;
    }
}
