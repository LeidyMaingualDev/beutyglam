package com.beutyglam.repository;

import java.util.List;

import com.beutyglam.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long userId);
}
