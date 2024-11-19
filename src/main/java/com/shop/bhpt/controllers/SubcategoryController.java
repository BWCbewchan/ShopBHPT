package com.shop.bhpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.dto.SubcategoryDTO;
import com.shop.bhpt.entities.Subcategory;
import com.shop.bhpt.repositories.SubcategoryRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "*")
@Slf4j
public class SubcategoryController {
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    @GetMapping
    public List<SubcategoryDTO> getAllSubcategories() {
        return subcategoryRepository.findAll()
                .stream()
                .map(subcategory -> new SubcategoryDTO(
                        subcategory.getId(),
                        subcategory.getCategory().getId(),
                        subcategory.getName(),
                        subcategory.getImg()
                ))
                .collect(Collectors.toList());
    }
} 