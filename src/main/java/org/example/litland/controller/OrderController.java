package org.example.litland.controller;

import org.example.litland.response.OrderResponse;
import org.example.litland.service.OrderService;
import org.example.litland.shell.UserInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/all")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<OrderResponse> getCartBooks(@RequestBody UserInfo userInfo) {
        return orderService.findAllByUser(userInfo.getUserId());
    }
}
