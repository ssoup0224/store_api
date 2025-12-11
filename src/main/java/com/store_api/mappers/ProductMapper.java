package com.store_api.mappers;

import com.store_api.products.dtos.ProductDto;
import com.store_api.products.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto.ProductResponse toDto(Product product);

    Product toEntity(ProductDto.ProductRequest productDto);

    @Mapping(target = "id", ignore = true)
    void update(ProductDto.ProductRequest productDto, @MappingTarget Product product);
}
