package com.shop.bhpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.entities.Item;
import com.shop.bhpt.repositories.ItemRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@Slf4j
public class ItemController {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @GetMapping
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }
    
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElse(null);
    }
    
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam(required = false) String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = query.toLowerCase();
        return itemRepository.findAll().stream()
            .filter(item -> 
                // Search in name
                item.getName().toLowerCase().contains(searchTerm) ||
                // Search in colors
                item.getColors().stream()
                    .anyMatch(color -> color.toLowerCase().contains(searchTerm)) ||
                // Search in sizes
                item.getSizes().stream()
                    .anyMatch(size -> size.toLowerCase().contains(searchTerm)) ||
                // Search in price range (if query is a number)
                (isNumeric(searchTerm) && 
                 item.getPrice() <= Double.parseDouble(searchTerm) * 1.1 && 
                 item.getPrice() >= Double.parseDouble(searchTerm) * 0.9)
            )
            .collect(Collectors.toList());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<Item> getItemsByCategory(@PathVariable Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public List<Item> getItemsBySubcategory(@PathVariable Long subcategoryId) {
        return itemRepository.findBySubcategoryId(subcategoryId);
    }
} 