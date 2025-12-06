package com.store_api.mappers;

import com.store_api.dtos.CartDto;
import com.store_api.dtos.CartItemDto;
import com.store_api.entities.Cart;
import com.store_api.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    CartDto toDto(Cart cart);

    //@Mapping(target = "totalPrice", expression = "java(cartItem.CartItemDtoResponse.getTotalPrice()")
    //CartItemDto.CartItemDtoResponse toDtoResponse(CartItem cartItem);

    CartItemDto toDto(CartItem cartItem);
}
