package com.store_api.controllers;

import com.store_api.dtos.ProductDto;
import com.store_api.entities.Product;
import com.store_api.mappers.ProductMapper;
import com.store_api.repositories.CategoryRepository;
import com.store_api.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    public List<ProductDto.ProductResponse> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    @PostMapping("")
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@RequestBody ProductDto.ProductRequest productDto,
            UriComponentsBuilder uriComponentsBuilder) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build(); // STATUS: 400 Bad Request
        }

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

        var responseDto = productMapper.toDto(product);

        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        /**
         * location of the new object/resource that is created
         * ex: localhost:8080/products/5
         */

        return ResponseEntity.created(uri).body(responseDto); // STATUS: 201 Created
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id,
            @RequestBody ProductDto.ProductRequest productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build(); // STATUS: 400 Bad Request
        }

        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product)); // STATUS: 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build(); // STATUS: 404 Not Found
        }

        productRepository.delete(product);
        return ResponseEntity.noContent().build(); // STATUS: 204 No Content
    }
}
