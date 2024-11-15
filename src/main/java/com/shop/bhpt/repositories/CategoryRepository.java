package com.shop.bhpt.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.bhpt.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
} 