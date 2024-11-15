package com.shop.bhpt.services;


import java.util.List;

import com.shop.bhpt.entities.Item;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);
    List<Item> getItemsBySubcategory(Long subcategoryId);
    List<Item> searchItems(String keyword);
    Item saveItem(Item item);
    void deleteItem(Long id);
} 