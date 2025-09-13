package com.demo.service;

import com.demo.dto.ProductEvent;
import com.demo.dto.ProductRequest;
import com.demo.dto.ProductSearchRequest;
import com.demo.dto.ProductUpdateRequest;
import com.demo.dto.User;
import com.demo.exception.NotFoundException;
import com.demo.feign.UserFeignClient;
import com.demo.kafka.config.ProductKafkaProducer;
import com.demo.model.Product;
import com.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductKafkaProducer productKafkaProducer;

    public List<Product> getProductsByUserId(long creatorId) {
        User user = userFeignClient.getAccountInfo(creatorId);
        return productRepo.findAllByCreatorId(creatorId);
    }

    public Page<Product> searchProducts(ProductSearchRequest searchRequest) {
        // Create pageable object
        Sort sort = Sort.by(
            "desc".equalsIgnoreCase(searchRequest.getSortDirection()) 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC, 
            searchRequest.getSortBy()
        );
        
        Pageable pageable = PageRequest.of(
            searchRequest.getPage(), 
            searchRequest.getSize(), 
            sort
        );

        // Call repository search method
        return productRepo.searchProducts(
            searchRequest.getId(),
            searchRequest.getName(),
            searchRequest.getDescription(),
            searchRequest.getCreatorId(),
            searchRequest.getMinPrice(),
            searchRequest.getMaxPrice(),
            pageable
        );
    }

    public Product createProduct(ProductRequest productRequest) {
        // Get current user from JWT
        Long currentUserId = getCurrentUserId();
        String currentUsername = getCurrentUsername();
        
        // Create new product
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCreatorId(currentUserId);
        
        Product savedProduct = productRepo.save(product);
        
        // Send Kafka event
        ProductEvent event = ProductEvent.created(
            savedProduct.getId(),
            savedProduct.getName(),
            savedProduct.getDescription(),
            savedProduct.getPrice(),
            savedProduct.getCreatorId(),
            currentUsername
        );
        productKafkaProducer.sendProductEvent(event);
        
        return savedProduct;
    }

    public Product updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        // Get current user from JWT
        Long currentUserId = getCurrentUserId();
        String currentUsername = getCurrentUsername();
        boolean isAdmin = isCurrentUserAdmin();
        
        // Find product and check permissions
        Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        
        Product product = productOpt.get();
        // Allow update if user is admin OR if user is the creator
        if (!isAdmin && !product.getCreatorId().equals(currentUserId)) {
            throw new RuntimeException("You can only update your own products or you need admin role");
        }
        
        // Update fields if provided
        if (updateRequest.getName() != null) {
            product.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            product.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getPrice() != null) {
            product.setPrice(updateRequest.getPrice());
        }
        
        Product updatedProduct = productRepo.save(product);
        
        // Send Kafka event
        ProductEvent event = ProductEvent.updated(
            updatedProduct.getId(),
            updatedProduct.getName(),
            updatedProduct.getDescription(),
            updatedProduct.getPrice(),
            updatedProduct.getCreatorId(),
            currentUsername
        );
        productKafkaProducer.sendProductEvent(event);
        
        return updatedProduct;
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof Long) {
            return (Long) authentication.getDetails();
        }
        throw new RuntimeException("User not authenticated");
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        throw new RuntimeException("User not authenticated");
    }

    private boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }
}
