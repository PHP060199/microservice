package com.example.orderservice.service.implement;

import com.example.inventoryservice.domain.Inventory;
import com.example.orderservice.client.InventoryServiceClient;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderLineItems;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderLineItemsDTO;
import com.example.orderservice.repository.OrderLineItemsRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderLineItemsService;
import com.example.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public void placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        //Stock

        List<Inventory> inventoryList = inventoryServiceClient.getCodesValid();

        List<String> skuCodesValid = inventoryList.stream().map(Inventory::getCode).toList();

        List<Inventory> remainingList = new ArrayList<>();

        orderDTO.getOrderLineItemsDTOList().forEach(orderLineItemsDTO -> {
            if (skuCodesValid.contains(orderLineItemsDTO.getSkuCode())) {

                Integer orderQuantity = orderLineItemsDTO.getQuantity();
                Integer stockQuantity = inventoryServiceClient.getQuantityByCode(orderLineItemsDTO.getSkuCode());

               if ( stockQuantity < orderQuantity) {
                   throw new IllegalArgumentException("Product with skuCode: " + orderLineItemsDTO.getSkuCode() + " not enough quantity left" );
               } else {
                   Inventory inventory = new Inventory();
                   inventory.setCode(orderLineItemsDTO.getSkuCode());
                   inventory.setQuantity(stockQuantity - orderQuantity);
                   remainingList.add(inventory);
               }
            } else {
                throw new IllegalArgumentException("Product with skuCode: " + orderLineItemsDTO.getSkuCode() + " is not in stock" );
            }
        });

        inventoryServiceClient.setQuantity(remainingList);
        Order finalOrder = orderRepository.save(order);
        orderDTO.getOrderLineItemsDTOList()
                .forEach(orderLineItemsDTO -> orderLineItemsService.placeOrderLineItemsService(orderLineItemsDTO, finalOrder.getId()));
    }

    @Override
    public OrderDTO getOrderDTOById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            OrderDTO orderDTO = new OrderDTO();
            List<OrderLineItems> orderLineItemsList = orderLineItemsRepository.findAllByOrderId(id);
            List<OrderLineItemsDTO> orderLineItemsDTOList = new ArrayList<>();
            orderLineItemsList.forEach(orderLineItems -> {
                orderLineItemsDTOList.add(modelMapper.map(orderLineItems, OrderLineItemsDTO.class));
            });
            orderDTO.setOrderLineItemsDTOList(orderLineItemsDTOList);
            return orderDTO;
        }
        return null;
    }
}
