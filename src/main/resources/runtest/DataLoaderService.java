package com.shop.bhpt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.shop.bhpt.entities.Category;
import com.shop.bhpt.entities.Subcategory;
import com.shop.bhpt.entities.Item;
import com.shop.bhpt.repositories.CategoryRepository;
import com.shop.bhpt.repositories.SubcategoryRepository;

import jakarta.annotation.PostConstruct;

import com.shop.bhpt.repositories.ItemRepository;

import org.springframework.core.io.ClassPathResource;



@Service
public class DataLoaderService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostConstruct
    @Transactional
    public void loadData() throws IOException {
        // Load categories
        List<Category> categories = objectMapper.readValue(
            new ClassPathResource("data/category.json").getFile(),
            new TypeReference<List<Category>>() {}
        );
        categoryRepository.saveAll(categories);
        
        // Load subcategories
        List<Subcategory> subcategories = objectMapper.readValue(
            new ClassPathResource("data/subcategory.json").getFile(),
            new TypeReference<List<Subcategory>>() {}
        );
        subcategoryRepository.saveAll(subcategories);
        
        // Load items
        List<Item> items = objectMapper.readValue(
            new ClassPathResource("data/item.json").getFile(),
            new TypeReference<List<Item>>() {}
        );
        itemRepository.saveAll(items);
    }
} 