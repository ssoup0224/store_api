package com.store_api.mappers;

import com.store_api.dtos.CartDto;
import com.store_api.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}
