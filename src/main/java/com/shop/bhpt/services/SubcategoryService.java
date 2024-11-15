package com.shop.bhpt.services;

import java.util.List;

import com.shop.bhpt.entities.Subcategory;

public interface SubcategoryService {
    List<Subcategory> getAllSubcategories();
    Subcategory getSubcategoryById(Long id);
    List<Subcategory> getSubcategoriesByCategory(Long categoryId);
    Subcategory saveSubcategory(Subcategory subcategory);
    void deleteSubcategory(Long id);
} 