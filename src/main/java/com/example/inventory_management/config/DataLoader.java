package com.example.inventory_management.config;

import com.example.inventory_management.model.Product;
import com.example.inventory_management.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {

            long currentCount = productRepository.count();

            for (int i = (int) currentCount + 1; i <= 1000; i++) {

                Product product = new Product(
                        "Product-" + i,
                        (i % 100) + 1,
                        (i % 500) + 10.0,
                        i % 2 == 0 ? "Electronics" : "Office"
                );

                productRepository.save(product);
            }

            System.out.println("Database now contains " + productRepository.count() + " products!");
        };
    }
}