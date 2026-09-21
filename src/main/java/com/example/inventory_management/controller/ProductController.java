package com.example.inventory_management.controller;
import com.example.inventory_management.dto.ProductDTO;
import com.example.inventory_management.dto.ProductResponseDTO;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.inventory_management.model.Product;
import com.example.inventory_management.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Inventory Management API is running!";
    }

    @GetMapping("/api/products")
    public List<ProductResponseDTO> getProducts() {
        return productService.getAllProducts();
    }
    @PostMapping("/api/products")
    public Product createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }
    @GetMapping("/api/products/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/api/products/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {

        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/api/products/search")
    public List<ProductResponseDTO> searchProducts(@RequestParam String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/api/products/low-stock")
    public List<ProductResponseDTO> getLowStockProducts(@RequestParam Integer quantity) {
        return productService.getLowStockProducts(quantity);
    }

    @GetMapping("/api/products/price")
    public List<ProductResponseDTO> getProductsAbovePrice(@RequestParam Double price) {
        return productService.getProductsAbovePrice(price);
    }

   @GetMapping("/api/products/category")
    public List<ProductResponseDTO> getProductsByCategory(@RequestParam String category){
        return productService.getProductsByCategory(category);
   }

    @PostMapping("/api/products/{id}/add-stock")
    public ProductResponseDTO addStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        return productService.addStock(id, quantity);
    }

    @PostMapping("/api/products/{id}/remove-stock")
    public ProductResponseDTO removeStock(
        @PathVariable Long id,
        @RequestParam Integer quantity) {

    return productService.removeStock(id, quantity);

    }
}

