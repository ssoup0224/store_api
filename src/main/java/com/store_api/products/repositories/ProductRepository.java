package com.store_api.products.repositories;

import com.store_api.products.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Byte categoryId);

    @EntityGraph(attributePaths = "category") // JOIN FETCH p.category
    @Query("SELECT p from Product p")
    List<Product> findAllWithCategory();
}
