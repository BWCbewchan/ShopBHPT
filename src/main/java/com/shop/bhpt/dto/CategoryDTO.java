package com.shop.bhpt.dto;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private List<SubcategoryDTO> subcategories;
    
    public CategoryDTO() {
		super();
	}
	public CategoryDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<SubcategoryDTO> getSubcategories() { return subcategories; }
    public void setSubcategories(List<SubcategoryDTO> subcategories) { 
        this.subcategories = subcategories; 
    }
} 