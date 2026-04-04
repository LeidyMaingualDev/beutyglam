package com.beutyglam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beutyglam.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
