package com.shop.bhpt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.bhpt.entities.Item;
import com.shop.bhpt.repositories.ItemRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@Slf4j
public class ItemController {
    
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping("/paginated")
    public Page<Item> getAllItemsWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findAll(pageable);
    }

    // Tìm theo subcategoryId với phân trang
    @GetMapping("/subcategory/{subcategoryId}/paginated")
    public Page<Item> getItemsBySubcategoryWithPagination(
        @PathVariable Long subcategoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.getBySubcategory_Id(subcategoryId, pageable);
    }


    // Tìm kiếm sản phẩm với nhiều điều kiện và phân trang
    @GetMapping("/searchPaginated")
    public Page<Item> searchItemsWithPagination(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Integer minDiscount,
        @RequestParam(required = false) Long subcategoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.searchItemsWithPage(name, minPrice, maxPrice, minDiscount, subcategoryId, pageable);
    }
    
    @GetMapping
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }
    
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElse(null);
    }
    
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam(required = false) String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = query.toLowerCase();
        return itemRepository.findAll().stream()
            .filter(item -> 
                // Search in name
                item.getName().toLowerCase().contains(searchTerm) ||
                // Search in colors
                item.getColors().stream()
                    .anyMatch(color -> color.toLowerCase().contains(searchTerm)) ||
                // Search in sizes
                item.getSizes().stream()
                    .anyMatch(size -> size.toLowerCase().contains(searchTerm)) ||
                // Search in price range (if query is a number)
                (isNumeric(searchTerm) && 
                 item.getPrice() <= Double.parseDouble(searchTerm) * 1.1 && 
                 item.getPrice() >= Double.parseDouble(searchTerm) * 0.9)
            )
            .collect(Collectors.toList());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<Item> getItemsByCategory(@PathVariable Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public List<Item> getItemsBySubcategory(@PathVariable Long subcategoryId) {
        return itemRepository.findBySubcategoryId(subcategoryId);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        System.out.println("Received item: " + item);
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        
        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        item.setDiscount(itemDetails.getDiscount());
        if (itemDetails.getColors() != null) {
            item.setColors(itemDetails.getColors());
        }
        if (itemDetails.getSizes() != null) {
            item.setSizes(itemDetails.getSizes());
        }
        if (itemDetails.getCharacteristics() != null) {
            item.setCharacteristics(itemDetails.getCharacteristics());
        }
        
        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }

    // API để quản lý số lượng tồn kho
    @PutMapping("/{id}/stock")
    public Item updateStockQuantity(
        @PathVariable Long id,
        @RequestParam int quantity
    ) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        item.setStockQuantity(quantity);
        return itemRepository.save(item);
    }

    // API để cập nhật số lượng đã bán
    @PutMapping("/{id}/sold")
    public Item updateSoldQuantity(
        @PathVariable Long id,
        @RequestParam int quantity
    ) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        item.setSoldQuantity(quantity);
        return itemRepository.save(item);
    }

    // API để lấy các sản phẩm sắp hết hàng
    @GetMapping("/low-stock")
    public List<Item> getLowStockItems(
        @RequestParam(defaultValue = "10") int threshold
    ) {
        return itemRepository.findByStockQuantityLessThan(threshold);
    }

    // API để lấy các sản phẩm bán chạy
    @GetMapping("/best-selling")
    public List<Item> getBestSellingItems(
        @RequestParam(defaultValue = "20") int threshold
    ) {
        return itemRepository.findBySoldQuantityGreaterThan(threshold);
    }

    // API để cập nhật nhiều thuộc tính cùng lúc
    @PutMapping("/{id}/full-update")
    public Item fullUpdateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        
        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        item.setDiscount(itemDetails.getDiscount());
        item.setStockQuantity(itemDetails.getStockQuantity());
        item.setSoldQuantity(itemDetails.getSoldQuantity());
        item.setColors(itemDetails.getColors());
        item.setSizes(itemDetails.getSizes());
        item.setCharacteristics(itemDetails.getCharacteristics());
        item.setSubcategory(itemDetails.getSubcategory());
        
        return itemRepository.save(item);
    }

    // API để tìm kiếm theo nhiều tiêu chí kết hợp
    @GetMapping("/advanced-search")
    public List<Item> advancedSearch(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Integer minStock,
        @RequestParam(required = false) Integer minSold,
        @RequestParam(required = false) Set<String> colors,
        @RequestParam(required = false) Set<String> sizes
    ) {
        // Implement advanced search logic here
        return itemRepository.findAll().stream()
            .filter(item -> 
                (name == null || item.getName().toLowerCase().contains(name.toLowerCase())) &&
                (minPrice == null || item.getPrice() >= minPrice) &&
                (maxPrice == null || item.getPrice() <= maxPrice) &&
                (minStock == null || item.getStockQuantity() >= minStock) &&
                (minSold == null || item.getSoldQuantity() >= minSold) &&
                (colors == null || item.getColors().containsAll(colors)) &&
                (sizes == null || item.getSizes().containsAll(sizes))
            )
            .collect(Collectors.toList());
    }
} 