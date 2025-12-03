package com.store_api.mappers;

import com.store_api.dtos.ProductDto;
import com.store_api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto.ProductInfo toDto(Product product);

    Product toEntity(ProductDto.ProductInfo productDto);

    @Mapping(target = "id", ignore = true)
    void update (ProductDto.ProductInfo productDto, @MappingTarget Product product);
}
