package com.pm.inventoryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Long productId;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;

    private String skuCode;

    // Constructors
    public Inventory() {}

    public Inventory(Long productId, int quantity, String skuCode) {
        this.productId = productId;
        this.quantity = quantity;
        this.skuCode = skuCode;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public boolean isInStock() {
        return this.quantity > 0;
    }
}
