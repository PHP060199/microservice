package com.example.orderservice.service;

import com.example.orderservice.dto.OrderLineItemsDTO;

public interface OrderLineItemsService {
    public void placeOrderLineItemsService(OrderLineItemsDTO orderLineItemsDTO, Long orderId);
}
