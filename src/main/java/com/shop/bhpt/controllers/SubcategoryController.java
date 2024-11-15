package com.shop.bhpt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.entities.Subcategory;
import com.shop.bhpt.repositories.SubcategoryRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "*")
@Slf4j
public class SubcategoryController {
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    @GetMapping
    public List<Subcategory> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return subcategories;
    }
} 