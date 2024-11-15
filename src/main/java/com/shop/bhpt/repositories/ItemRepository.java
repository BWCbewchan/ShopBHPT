package com.shop.bhpt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.bhpt.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findBySubcategoryId(Long subcategoryId);
	
	// Tìm theo tên (case insensitive)
	List<Item> findByNameContainingIgnoreCase(String name);
	
	// Tìm theo khoảng giá
	List<Item> findByPriceBetween(double minPrice, double maxPrice);
	
	// Tìm theo discount tối thiểu
	List<Item> findByDiscountGreaterThanEqual(int minDiscount);
	
	// Tìm kết hợp nhiều điều kiện
	@Query("SELECT i FROM Item i WHERE " +
		   "(:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
		   "(:minPrice IS NULL OR i.price >= :minPrice) AND " +
		   "(:maxPrice IS NULL OR i.price <= :maxPrice) AND " +
		   "(:minDiscount IS NULL OR i.discount >= :minDiscount) AND " +
		   "(:subcategoryId IS NULL OR i.subcategory.id = :subcategoryId)")
	List<Item> searchItems(
		@Param("name") String name,
		@Param("minPrice") Double minPrice,
		@Param("maxPrice") Double maxPrice,
		@Param("minDiscount") Integer minDiscount,
		@Param("subcategoryId") Long subcategoryId
	);
} 