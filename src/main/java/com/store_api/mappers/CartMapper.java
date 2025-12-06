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
    @Mapping(target = "totalPrice", expression = "java(cart.getCartItems().stream().map(item -> item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity()))).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add))")
    CartDto toDto(Cart cart);

    // @Mapping(target = "totalPrice", expression =
    // "java(cartItem.CartItemDtoResponse.getTotalPrice()")
    // CartItemDto.CartItemDtoResponse toDtoResponse(CartItem cartItem);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())))")
    CartItemDto.CartItemResponse toDto(CartItem cartItem);

    CartItemDto.CartProductDto toDto(Product product);
}
