package com.example.inventory_management.repository;

import com.example.inventory_management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findByQuantityLessThanEqual(Integer quantity);
    List<Product> findByPriceGreaterThanEqual(Double price);
}