package com.shop.bhpt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.shop.bhpt.entities.Order;
import com.shop.bhpt.entities.User;

@NoRepositoryBean
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
} 