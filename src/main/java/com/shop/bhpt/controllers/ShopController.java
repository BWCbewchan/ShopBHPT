package com.shop.bhpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.dto.CategoryDTO;
import com.shop.bhpt.dto.SubcategoryDTO;
import com.shop.bhpt.entities.Category;
import com.shop.bhpt.entities.Item;
import com.shop.bhpt.entities.Subcategory;
import com.shop.bhpt.repositories.CategoryRepository;
import com.shop.bhpt.repositories.ItemRepository;
import com.shop.bhpt.repositories.SubcategoryRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "*")
@Slf4j
public class ShopController {
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
            .map(this::convertToCategoryDTO)
            .collect(Collectors.toList());
    }
    
    private CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        
        List<SubcategoryDTO> subDTOs = new ArrayList<>();
        for (Subcategory sub : category.getSubcategories()) {
            SubcategoryDTO subDTO = new SubcategoryDTO();
            subDTO.setId(sub.getId());
            subDTO.setName(sub.getName());
            subDTO.setImg(sub.getImg());
            subDTOs.add(subDTO);
        }
        categoryDTO.setSubcategories(subDTOs);
        
        return categoryDTO;
    }
    
    @GetMapping("/subcategories")
    public List<Subcategory> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return subcategories;
    }
    
    @GetMapping("/items")
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }
} 