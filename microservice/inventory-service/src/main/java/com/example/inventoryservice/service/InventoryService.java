package com.example.inventoryservice.service;

import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    boolean isInStock(String code);

    List<Inventory> codesListInStocks();

    Integer getQuantityCode(String code);

    void setQuantity(List<Inventory> inventoryList);

    Inventory addInventory(Inventory inventory);

    Inventory updateQuantity(Long id, Integer quantity);



}
