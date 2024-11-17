package com.shop.bhpt.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private Integer sold;
    private Double price;
    private Integer discount;
    private Long subcategoryId;
    private List<String> colors;
    private List<String> sizes;
    private List<String> characteristics;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSold() {
		return sold;
	}
	public void setSold(Integer sold) {
		this.sold = sold;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Long getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getSizes() {
		return sizes;
	}
	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}
	public List<String> getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(List<String> characteristics) {
		this.characteristics = characteristics;
	}
    
} 