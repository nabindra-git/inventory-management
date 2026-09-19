package com.example.inventory_management.controller;
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
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }
    @PostMapping("/api/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }
    @GetMapping("/api/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/api/products/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/api/products/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/api/products/low-stock")
    public List<Product> getLowStockProduct(@RequestParam Integer quantity){
        return productService.getLowStockProduct(quantity);
   }

   @GetMapping("/api/products/price")
    public List<Product> getProductsAbovePrice(@RequestParam Double price){
        return productService.getProductsAbovePrice(price);
   }

}

