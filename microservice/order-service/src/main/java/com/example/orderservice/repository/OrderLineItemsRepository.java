package com.example.orderservice.repository;

import com.example.orderservice.domain.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {
    List<OrderLineItems> findAllByOrderId(Long orderId);
}
