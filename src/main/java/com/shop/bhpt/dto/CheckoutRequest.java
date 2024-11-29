package com.shop.bhpt.dto;

import java.util.List;

import com.shop.bhpt.entities.OrderItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotNull(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;
    
    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    private List<OrderItem> items;
    
    private String phone;
    private String paymentMethod;
    
    
	public CheckoutRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CheckoutRequest(String shippingAddress, String phone, String paymentMethod) {
		super();
		this.shippingAddress = shippingAddress;
		this.phone = phone;
		this.paymentMethod = paymentMethod;
	}


	public String getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
    
    
} 