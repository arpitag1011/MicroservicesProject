package com.example.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class OrderController {
    @GetMapping("/orders")
    public List<String> getOrders() {
        return List.of("Order-001", "Order-002", "Order-003");
    }
}
