package com.pm.inventoryservice.controller;

import com.pm.inventoryservice.model.Inventory;
import com.pm.inventoryservice.repository.InventoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getByProductId(@PathVariable Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}/in-stock")
    public ResponseEntity<Boolean> isInStock(@PathVariable Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(inv -> ResponseEntity.ok(inv.isInStock()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@Valid @RequestBody Inventory inventory) {
        Inventory saved = inventoryRepository.save(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/product/{productId}/quantity")
    public ResponseEntity<Inventory> updateQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inv -> {
                    inv.setQuantity(quantity);
                    return ResponseEntity.ok(inventoryRepository.save(inv));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
