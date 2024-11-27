package com.shop.bhpt.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private Long orderId;
    private String status;
    private String transactionId;
    private String message;
    
    
	public PaymentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentResponse(Long orderId, String status, String transactionId, String message) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.transactionId = transactionId;
		this.message = message;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
} 