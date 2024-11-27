package com.shop.bhpt.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long itemId;
    private int quantity;
    private String size;
    private String color;
	public CartItemRequest(Long itemId, int quantity, String size, String color) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.size = size;
		this.color = color;
	}
	public CartItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
    
    
} 