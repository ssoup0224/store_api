package com.store_api.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ProductDto {

    @Getter @Setter
    public static class ProductInfo {
        private Long id;
        private String name;
        private BigDecimal price;
        private String description;
        private Byte categoryId;
    }

}
