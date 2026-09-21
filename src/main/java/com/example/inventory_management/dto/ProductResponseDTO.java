package com.example.inventory_management.dto;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private String category;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, Integer quantity, Double price, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}