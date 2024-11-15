package com.shop.bhpt.services;

import java.util.List;

import com.shop.bhpt.entities.Category;


public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category save(Category category);
} 