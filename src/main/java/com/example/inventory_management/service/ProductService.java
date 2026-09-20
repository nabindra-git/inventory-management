package com.example.inventory_management.service;

import com.example.inventory_management.model.Product;
import com.example.inventory_management.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));
    }
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Product not found");
        }

        product.setName(updatedProduct.getName());
        product.setQuantity(updatedProduct.getQuantity());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found"
            );
        }

        productRepository.deleteById(id);
    }

    public List<Product> searchByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> getLowStockProduct(Integer quantity){
        return productRepository.findByQuantityLessThanEqual(quantity);
    }

    public List<Product> getProductsAbovePrice(Double price){
        return productRepository.findByPriceGreaterThanEqual(price);
    }

    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }

    public Product addStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));
        if (quantity <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than 0"
            );
        }

        product.setQuantity(product.getQuantity() + quantity);

        return productRepository.save(product);
    }

    public Product removeStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));


        if (product.getQuantity() < quantity) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Not enough stock"
            );
        }
        if (quantity <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than 0"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);

        return productRepository.save(product);
    }
}
