package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{cartId}/place")
    public Order placeOrder(@PathVariable Long cartId) {
        return orderService.placeOrder(cartId);
    }

    @GetMapping("/{orderCode}")
    public Order getOrder(@PathVariable String orderCode) {
        return orderService.getOrderForCode(orderCode);
    }
}
