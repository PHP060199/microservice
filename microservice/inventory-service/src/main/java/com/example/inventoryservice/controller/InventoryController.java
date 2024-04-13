package com.example.inventoryservice.controller;

import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam ("code") String code ){
        return inventoryService.isInStock(code);
    }

    @GetMapping("/getQuantityBySkuCode")
//    @ResponseStatus(HttpStatus.OK)
    public Integer getQuantityByCode(@RequestParam("code") String code){
        return inventoryService.getQuantityCode(code);
    }

    @PutMapping("/setQuantity")
//    @ResponseStatus(HttpStatus.OK)
    public void setQuantity(@RequestBody List<Inventory> inventoryList){
        inventoryService.setQuantity(inventoryList);
    }

    @GetMapping("/skuCodesValid")
//    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getCodesValid(){
        return inventoryService.codesListInStocks();
    }

    @PostMapping("/addInventory")
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return inventoryService.addInventory(inventory);
    }

    @PutMapping("/updateQuantity")
    public Inventory updateQuantity(@RequestParam("id") Long id,
                                    @RequestParam("quantity") Integer quantity) {
        return inventoryService.updateQuantity(id, quantity);
    }
}
