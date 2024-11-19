package com.shop.bhpt.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SubcategoryDTO {
    private Long id;
    private Long category_id;
    private String name;
    private String img;
    
    
	public SubcategoryDTO() {
		super();
	}


	public SubcategoryDTO(Long id, Long category_id, String name, String img) {
		super();
		this.id = id;
		this.category_id = category_id;
		this.name = name;
		this.img = img;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCategory_id() {
		return category_id;
	}


	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}
    
    
}