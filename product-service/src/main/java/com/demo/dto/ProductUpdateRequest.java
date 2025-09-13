package com.demo.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    
    private String name;
    
    private String description;
    
    @Positive(message = "Price must be positive")
    private BigDecimal price;
}

