package com.shop.bhpt.dto;

import lombok.Data;

@Data
public class PaymentStatus {
    private Long orderId;
    private String status;
    private String lastUpdated;
    
    
	public PaymentStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentStatus(Long orderId, String status, String lastUpdated) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.lastUpdated = lastUpdated;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
    
    
} 