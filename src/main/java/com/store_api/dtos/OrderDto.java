package com.store_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
