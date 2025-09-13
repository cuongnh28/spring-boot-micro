package com.demo.controller;

import com.demo.dto.ProductRequest;
import com.demo.dto.ProductSearchRequest;
import com.demo.dto.ProductUpdateRequest;
import com.demo.model.Product;
import com.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> getProductsByUserId(@RequestParam long userId) {
        return productService.getProductsByUserId(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(ProductSearchRequest searchRequest) {
        Page<Product> products = productService.searchProducts(searchRequest);
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody ProductUpdateRequest updateRequest) {
        Product updatedProduct = productService.updateProduct(id, updateRequest);
        return ResponseEntity.ok(updatedProduct);
    }

}
