package com.demo.service;

import com.demo.dto.User;
import com.demo.feign.UserFeignClient;
import com.demo.model.Product;
import com.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserFeignClient userFeignClient;


    public List<Product> getProductsByUserId(long createdBy) {
        User user = userFeignClient.getAccountInfo(createdBy);
        return productRepo.findAllByCreatedBy(createdBy);
    }

}
