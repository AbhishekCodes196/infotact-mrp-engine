package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // This method gets all products from your database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // This method saves a new product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}