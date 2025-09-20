package org.example.hightrafficproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.hightrafficproject.entity.Order;
import org.example.hightrafficproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public static record CreateOrderRequest(
            @NotBlank String userId,
            @NotNull Long productId,
            @Min(1) int quantity
    ) {}

    public static record CreateOrderResponse(Long orderId) {}

    public static record OrderDto(
            Long id,
            String userId,
            Long productId,
            Integer quantity,
            Long totalPrice,
            java.time.Instant createdAt,
            String status
    ) {}

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.userId(), request.productId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateOrderResponse(order.getId()));
    }

    @GetMapping
    public List<OrderDto> getByUser(@RequestParam String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return orders.stream()
                .map(o -> new OrderDto(
                        o.getId(),
                        o.getUserId(),
                        o.getProduct().getId(),
                        o.getQuantity(),
                        o.getTotalPrice(),
                        o.getCreatedAt(),
                        o.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
