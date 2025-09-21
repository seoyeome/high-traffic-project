package org.example.hightrafficproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hightrafficproject.api.order.request.CreateOrderRequest;
import org.example.hightrafficproject.api.order.response.CreateOrderResponse;
import org.example.hightrafficproject.api.order.response.OrderDto;
import org.example.hightrafficproject.entity.Order;
import org.example.hightrafficproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.userId(), request.productId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateOrderResponse(order.getId()));
    }

    @GetMapping
    public List<OrderDto> getByUser(@RequestParam String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return orders.stream().map(OrderDto::from).toList();
    }
}
