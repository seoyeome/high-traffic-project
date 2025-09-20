package org.example.hightrafficproject.repository;

import org.example.hightrafficproject.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"product"})
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}
