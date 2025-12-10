package com.store_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDto {

    @Getter
    @Setter
    public static class CheckOutRequest {
        @NotNull(message = "Cart ID is required.")
        private UUID cartId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CheckOutResponse {
        private Long orderId;
    }

    @Getter
    @Setter
    public static class OrderInfoDto {
        private Long id;
        private String status;
        private LocalDateTime createdAt;
        private List<OrderItemDto.OrderItemResponse> items;
        private BigDecimal totalPrice;
    }
}
