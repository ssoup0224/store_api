package com.store_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ProductDto {

    @Getter
    @Setter
    public static class ProductRequest {
        private String name;
        private BigDecimal price;
        private String description;
        private Byte categoryId;
    }

    @Getter
    @Setter
    public static class ProductResponse {
        private Long id;
        private String name;
        private BigDecimal price;
        private String description;
        private Byte categoryId;
    }

}
