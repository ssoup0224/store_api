package com.store_api.mappers;

import com.store_api.dtos.CartDto;
import com.store_api.dtos.CartItemDto;
import com.store_api.entities.Cart;
import com.store_api.entities.CartItem;
import com.store_api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    // @Mapping(target = "totalPrice", expression =
    // "java(cartItem.CartItemDtoResponse.getTotalPrice()")
    // CartItemDto.CartItemDtoResponse toDtoResponse(CartItem cartItem);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto.CartItemResponse toDto(CartItem cartItem);

    CartItemDto.CartProductDto toDto(Product product);

    CartItemDto.UpdateCartItemRequest toDtoRequest(CartItem cartItem);
}
