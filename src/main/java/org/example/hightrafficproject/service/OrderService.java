package org.example.hightrafficproject.service;

import lombok.RequiredArgsConstructor;
import org.example.hightrafficproject.entity.Order;
import org.example.hightrafficproject.entity.Product;
import org.example.hightrafficproject.repository.OrderRepository;
import org.example.hightrafficproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(String userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be >= 1");
        }
        if (product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Order order = Order.builder()
                .userId(userId)
                .product(product)
                .quantity(quantity)
                .totalPrice(product.getPrice() * quantity)
                .createdAt(Instant.now())
                .status("CREATED")
                .build();
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
