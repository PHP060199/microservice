package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;

public interface OrderService {
    public void placeOrder(OrderDTO orderDTO);

    public OrderDTO getOrderDTOById(Long id);
}
