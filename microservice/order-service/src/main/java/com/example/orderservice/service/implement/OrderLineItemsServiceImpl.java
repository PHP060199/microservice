package com.example.orderservice.service.implement;

import com.example.orderservice.domain.OrderLineItems;
import com.example.orderservice.dto.OrderLineItemsDTO;
import com.example.orderservice.repository.OrderLineItemsRepository;
import com.example.orderservice.service.OrderLineItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineItemsServiceImpl implements OrderLineItemsService {

    private final OrderLineItemsRepository orderLineItemsRepository;
    @Override
    public void placeOrderLineItemsService(OrderLineItemsDTO orderLineItemsDTO, Long orderId) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setOrderId(orderId);
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItemsRepository.save(orderLineItems);
    }
}
