package com.example.inventoryservice.service.implement;


import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.dto.InventoryDTO;
import com.example.inventoryservice.exception.AttributeExistsException;
import com.example.inventoryservice.exception.NotFoundException;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String code) {
        return inventoryRepository.findByCode(code).isPresent();
    }

    @Override
    public List<Inventory> codesListInStocks() {
        return inventoryRepository.findAllByQuantityGreaterThan(0);
    }

    @Override
    public Integer getQuantityCode(String code) {
        Inventory inventory = inventoryRepository.findByCode(code).orElse(null);
        if (inventory != null) {
            return inventory.getQuantity();
        }
        return 0;
    }

    @Override
    @Transactional
    public void setQuantity(List<Inventory> inventoryList) {

        inventoryList.forEach(inventory ->{
            Inventory inv = inventoryRepository.findByCode(inventory.getCode()).orElse(null);
            if (inv != null) {
                inv.setQuantity(inventory.getQuantity());
                inventoryRepository.save(inv);
            }
        });
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        Inventory inv = inventoryRepository.findByCode(inventory.getCode()).orElse(null);
        if (inv != null) {
            throw new AttributeExistsException("Code is exists!");
        }
        Inventory newInventory = new Inventory();
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setCode(inventory.getCode());
        newInventory =inventoryRepository.save(newInventory);
        return newInventory;
    }

    @Override
    public Inventory updateQuantity(Long id, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
        return inventory;
    }
}
