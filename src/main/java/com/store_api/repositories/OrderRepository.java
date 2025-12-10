package com.store_api.repositories;

import com.store_api.entities.Order;
import com.store_api.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.customer = :customer") // to solve N+1 problem
    List<Order> findAllByCustomer(@Param("customer") User customer);
}
