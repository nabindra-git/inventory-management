package com.example.inventory_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;

    private String category;

    // default constructor
    public Product(){

    }

    // Creating a constructor to create objects
    public Product(String name, Integer quantity, Double price, String category){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    // Getter for id
    public Long getId(){
        return id;
    }

    // Now getters and setters set up
    // Getters and Setters for name
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    // Getters and Setters for quantity
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
    public Integer getQuantity(){
        return quantity;
    }

    // Getters and Setters for price
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice(){
        return price;
    }

    // Getters and Setters for category
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return category;
    }
}

