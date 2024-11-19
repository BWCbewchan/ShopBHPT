package com.shop.bhpt.controllers;

import com.shop.bhpt.dto.CategoryDTO;
import com.shop.bhpt.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()
                ))
                .collect(Collectors.toList());
    }
} 