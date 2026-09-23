package com.example.inventory_management.service;
import com.example.inventory_management.model.Product;
import com.example.inventory_management.dto.ProductResponseDTO;
import com.example.inventory_management.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.inventory_management.dto.ProductDTO;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void testGetProductById() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getProductById(1L);

        assertEquals("Laptop", result.getName());
        assertEquals(10, result.getQuantity());
        assertEquals(999.99, result.getPrice());
        assertEquals("Electronics", result.getCategory());
    }

    @Test
    void testGetProductByIdNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> productService.getProductById(99L)
        );
    }

    @Test
    void testUpdateProduct() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        ProductDTO updatedProduct = new ProductDTO(
                "Gaming Laptop",
                5,
                1499.99,
                "Computers"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("Gaming Laptop", result.getName());
        assertEquals(5, result.getQuantity());
        assertEquals(1499.99, result.getPrice());
        assertEquals("Computers", result.getCategory());
    }

    @Test
    void testDeleteProduct() {

        when(productRepository.existsById(1L))
                .thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void testAddStock() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        ProductResponseDTO result = productService.addStock(1L, 5);

        assertEquals(15, result.getQuantity());
    }

    @Test
    void testRemoveStock() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        ProductResponseDTO result = productService.removeStock(1L, 3);

        assertEquals(7, result.getQuantity());
    }

    @Test
    void testRemoveStockInsufficientStock() {

        Product product = new Product(
                "Laptop",
                5,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                () -> productService.removeStock(1L, 10)
        );
    }

    @Test
    void testAddStockInvalidQuantity() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                () -> productService.addStock(1L, 0)
        );
    }

    @Test
    void testRemoveStockInvalidQuantity() {

        Product product = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                () -> productService.removeStock(1L, 0)
        );
    }
}
