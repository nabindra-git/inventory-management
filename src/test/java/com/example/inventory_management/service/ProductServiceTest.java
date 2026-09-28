package com.example.inventory_management.service;
import com.example.inventory_management.model.Product;
import com.example.inventory_management.dto.ProductResponseDTO;
import com.example.inventory_management.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.inventory_management.dto.ProductDTO;
import static org.mockito.Mockito.verify;

import java.util.List;
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
    @Test
    void testCreateProduct() {

        ProductDTO productDTO = new ProductDTO(
                "Keyboard",
                20,
                49.99,
                "Electronics"
        );

        Product product = new Product(
                "Keyboard",
                20,
                49.99,
                "Electronics"
        );

        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(product);

        Product result = productService.createProduct(productDTO);

        assertEquals("Keyboard", result.getName());
        assertEquals(20, result.getQuantity());
        assertEquals(49.99, result.getPrice());
        assertEquals("Electronics", result.getCategory());

        verify(productRepository).save(org.mockito.ArgumentMatchers.any(Product.class));
    }
    @Test
    void testGetProductsPagination() {

        Product product1 = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        Product product2 = new Product(
                "Keyboard",
                20,
                49.99,
                "Electronics"
        );

        List<Product> products = List.of(product1, product2);

        Page<Product> productPage = new PageImpl<>(products);

        Pageable pageable = PageRequest.of(0, 2);

        when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        Page<ProductResponseDTO> result =
                productService.getProducts(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Laptop", result.getContent().get(0).getName());
        assertEquals("Keyboard", result.getContent().get(1).getName());
    }
    @Test
    void testGetProductsSorting() {

        Product product1 = new Product(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        Product product2 = new Product(
                "Keyboard",
                20,
                49.99,
                "Electronics"
        );

        List<Product> products = List.of(product2, product1);

        Page<Product> productPage = new PageImpl<>(products);

        Pageable pageable = PageRequest.of(
                0,
                2,
                org.springframework.data.domain.Sort.by("price").ascending()
        );

        when(productRepository.findAll(pageable))
                .thenReturn(productPage);

        Page<ProductResponseDTO> result =
                productService.getProducts(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Keyboard", result.getContent().get(0).getName());
        assertEquals("Laptop", result.getContent().get(1).getName());
    }
    @Test
    void testUpdateProductNotFound() {

        ProductDTO productDTO = new ProductDTO(
                "Laptop",
                10,
                999.99,
                "Electronics"
        );

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> productService.updateProduct(99L, productDTO)
        );
    }
    @Test
    void testDeleteProductNotFound() {

        when(productRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(
                ResponseStatusException.class,
                () -> productService.deleteProduct(99L)
        );
    }
}
