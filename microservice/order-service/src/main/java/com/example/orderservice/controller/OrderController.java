package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placedOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return "Order Placed Successfully";
    }

    public String fallbackMethod(OrderDTO orderDTO, RuntimeException runtimeException ) {
        return "Oops! Something went wrong, please order after some time!";
    }

    @GetMapping("/allOrderById")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getAllOrderById(@RequestParam Long orderId) {
        return orderService.getOrderDTOById(orderId);
    }
}
