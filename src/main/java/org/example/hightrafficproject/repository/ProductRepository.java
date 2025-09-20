package org.example.hightrafficproject.repository;

import org.example.hightrafficproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
