package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    
    private String eventType; // CREATED, UPDATED, DELETED
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Long creatorId;
    private LocalDateTime timestamp;
    private String username;
}





