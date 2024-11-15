package com.shop.bhpt.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.bhpt.entities.Item;
import com.shop.bhpt.repositories.ItemRepository;
import com.shop.bhpt.services.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }
    
    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

	@Override
	public List<Item> getItemsBySubcategory(Long subcategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteItem(Long id) {
		// TODO Auto-generated method stub
		
	}
} 