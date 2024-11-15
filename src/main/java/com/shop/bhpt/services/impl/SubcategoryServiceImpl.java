package com.shop.bhpt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.bhpt.entities.Subcategory;
import com.shop.bhpt.repositories.SubcategoryRepository;
import com.shop.bhpt.services.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    @Override
    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }
    
    @Override
    public Subcategory saveSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

	@Override
	public Subcategory getSubcategoryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSubcategory(Long id) {
		// TODO Auto-generated method stub
		
	}
} 