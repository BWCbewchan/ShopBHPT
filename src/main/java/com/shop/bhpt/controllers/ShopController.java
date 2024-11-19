package com.shop.bhpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.dto.CategoryDTO;
import com.shop.bhpt.dto.ItemDTO;
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
		return categories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
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
	@GetMapping("items/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return convertToItemDTO(item);
    }
	@GetMapping("/items")
	public List<ItemDTO> getAllItems() {
	    List<Item> items = itemRepository.findAll();
	    return items.stream()
	        .map(this::convertToItemDTO)
	        .collect(Collectors.toList());
	}

	private ItemDTO convertToItemDTO(Item item) {
	    ItemDTO dto = new ItemDTO();
	    dto.setId(item.getId());
	    dto.setName(item.getName());
	    dto.setSold(item.getSold());
	    dto.setPrice(item.getPrice());
	    dto.setDiscount(item.getDiscount());
	    
	    if (item.getSubcategory() != null) {
	        dto.setSubcategoryId(item.getSubcategory().getId());
	        dto.setSubcategoryImg(item.getSubcategory().getImg());
	    }
	    
	    dto.setColors(new ArrayList<>(item.getColors()));
	    dto.setSizes(new ArrayList<>(item.getSizes()));
	    dto.setCharacteristics(new ArrayList<>(item.getCharacteristics()));
	    
	    return dto;
	}
}