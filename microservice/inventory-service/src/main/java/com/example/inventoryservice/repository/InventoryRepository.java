package com.example.inventoryservice.repository;

import com.example.inventoryservice.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByCode(String code);
    List<Inventory> findAllByQuantityGreaterThan(Integer quantity);


}
