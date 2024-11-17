package com.shop.bhpt.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private int sold;
    private double price;
    private int discount;
    
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @JsonBackReference
    private Subcategory subcategory;
    
    @ElementCollection
    @CollectionTable(name = "item_colors", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "color")
    private Set<String> colors;
    
    @ElementCollection
    @CollectionTable(name = "item_sizes", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    private Set<String> sizes;
    
    @ElementCollection
    @CollectionTable(name = "item_characteristics", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "characteristic")
    private Set<String> characteristics;

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

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public Set<String> getColors() {
		return colors;
	}

	public void setColors(Set<String> colors) {
		this.colors = colors;
	}

	public Set<String> getSizes() {
		return sizes;
	}

	public void setSizes(Set<String> sizes) {
		this.sizes = sizes;
	}

	public Set<String> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(Set<String> characteristics) {
		this.characteristics = characteristics;
	}

	public Long getSubcategoryId() {
		return subcategory != null ? subcategory.getId() : null;
	}
    
    
}