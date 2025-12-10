package com.store_api.mappers;

import com.store_api.dtos.OrderDto;
import org.mapstruct.Mapper;

import com.store_api.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto.OrderInfoDto toDto(Order order);
}
