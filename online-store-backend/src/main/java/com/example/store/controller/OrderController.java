package com.example.store.controller;

import com.example.store.entity.Order;
import com.example.store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Map<String, Object> payload) {
        UUID userId = UUID.fromString((String) payload.get("userId"));
        List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
        return orderService.createOrder(userId, items);
    }

    @GetMapping("/me/{userId}")
    public List<Order> getOrders(@PathVariable UUID userId) {
        return orderService.getOrdersByUser(userId);
    }
}
