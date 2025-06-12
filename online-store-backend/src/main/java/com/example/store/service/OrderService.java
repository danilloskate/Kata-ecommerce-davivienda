package com.example.store.service;

import com.example.store.entity.*;
import com.example.store.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(UUID userId, List<Map<String, Object>> items) {
        User user = userRepository.findById(userId).orElseThrow();
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(0.0);
        order = orderRepository.save(order);

        double total = 0.0;
        for (Map<String, Object> item : items) {
            UUID productId = UUID.fromString((String) item.get("productId"));
            int quantity = (Integer) item.get("quantity");

            Product product = productRepository.findById(productId).orElseThrow();
            if (product.getStock() < quantity) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(product.getPrice() * quantity);
            orderItemRepository.save(orderItem);

            total += product.getPrice() * quantity;
        }

        order.setTotal(total);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return orderRepository.findByUser(user);
    }
}
