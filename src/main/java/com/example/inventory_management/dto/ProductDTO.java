package com.example.inventory_management.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;

    private String category;

    // No-argument constructor
    public ProductDTO() {
    }

    // Constructor
    public ProductDTO(String name, Integer quantity, Double price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    // Name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Price
    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    // Category
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}