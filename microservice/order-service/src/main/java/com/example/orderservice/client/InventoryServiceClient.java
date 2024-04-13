package com.example.orderservice.client;

import com.example.inventoryservice.domain.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryServiceClient {

    @GetMapping(value = {"/api/inventory/skuCodesValid"})
    List<Inventory> getCodesValid();

    @PutMapping("/api/inventory/setQuantity")
    void setQuantity(@RequestBody List<Inventory> inventoryList);

    @GetMapping("/api/inventory/getQuantityBySkuCode")
    Integer getQuantityByCode(@RequestParam("code") String code);

}
