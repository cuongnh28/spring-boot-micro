package com.demo.config;

import com.demo.model.Product;
import com.demo.repo.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepo productRepo) {
        return args -> {
            if (productRepo.count() == 0) {
                Product p1 = new Product();
                p1.setName("Sample Product A");
                p1.setCreatedBy(1L);

                Product p2 = new Product();
                p2.setName("Sample Product B");
                p2.setCreatedBy(1L);

                productRepo.saveAll(Arrays.asList(p1, p2));
            }
        };
    }
}


