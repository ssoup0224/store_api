package com.store_api.repositories;

import com.store_api.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @EntityGraph(attributePaths = "cartItems.product") // items + products of each item
    @Query("SELECT c FROM Cart c WHERE c.id = :cart_id")
    Optional<Cart> getCartWithItems(@Param("cart_id") UUID id);
}
