package com.shop.bhpt.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.bhpt.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	Page<Item> findAll(Pageable pageable);
	Page<Item> getBySubcategory_Id(Long subcategoryId, Pageable pageable);
	List<Item> findBySubcategory_Id(Long subcategoryId);
	@Query("SELECT i FROM Item i WHERE i.soldQuantity > 0 ORDER BY i.soldQuantity DESC")
	Page<Item> findTop10SellingItems(Pageable pageable);

	@Query("SELECT i FROM Item i WHERE i.soldQuantity > 0 ORDER BY i.soldQuantity ASC")
	Page<Item> findTop10SlowSellingItems(Pageable pageable);


	@Query("SELECT i FROM Item i WHERE i.stockQuantity > 0 AND i.stockQuantity <= 10")
	Page<Item> findLowStockItems(Pageable pageable);

	@Query("SELECT i FROM Item i WHERE i.stockQuantity = 0")
	Page<Item> findOutOfStockItems(Pageable pageable);

	
	// Tìm theo tên (case insensitive)
	List<Item> findByNameContainingIgnoreCase(String name);
	
	// Tìm theo khoảng giá
	List<Item> findByPriceBetween(double minPrice, double maxPrice);
	
	// Tìm theo discount tối thiểu
	List<Item> findByDiscountGreaterThanEqual(int minDiscount);
	
	// Tìm kết hợp nhiều điều kiện
	@Query("SELECT i FROM Item i " +
		       "JOIN FETCH i.subcategory s " +
		       "LEFT JOIN FETCH s.category c " +
		       "WHERE (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
		       "(:minPrice IS NULL OR i.price >= :minPrice) AND " +
		       "(:maxPrice IS NULL OR i.price <= :maxPrice) AND " +
		       "(:minDiscount IS NULL OR i.discount >= :minDiscount) AND " +
		       "(:subcategoryId IS NULL OR s.id = :subcategoryId)")
		List<Item> searchItems(
		    @Param("name") String name,
		    @Param("minPrice") Double minPrice,
		    @Param("maxPrice") Double maxPrice,
		    @Param("minDiscount") Integer minDiscount,
		    @Param("subcategoryId") Long subcategoryId
		);
	@Query("SELECT i FROM Item i " +
	           "JOIN FETCH i.subcategory s " +
	           "LEFT JOIN FETCH s.category c " +
	           "WHERE (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
	           "(:minPrice IS NULL OR i.price >= :minPrice) AND " +
	           "(:maxPrice IS NULL OR i.price <= :maxPrice) AND " +
	           "(:minDiscount IS NULL OR i.discount >= :minDiscount) AND " +
	           "(:subcategoryId IS NULL OR s.id = :subcategoryId)")
	    Page<Item> searchItemsWithPage(
	        @Param("name") String name,
	        @Param("minPrice") Double minPrice,
	        @Param("maxPrice") Double maxPrice,
	        @Param("minDiscount") Integer minDiscount,
	        @Param("subcategoryId") Long subcategoryId,
	        Pageable pageable
	    );

	
	// Tìm theo category thông qua subcategory
	@Query("SELECT i FROM Item i JOIN FETCH i.subcategory s JOIN FETCH s.category c WHERE c.id = :categoryId")
	List<Item> findByCategoryId(@Param("categoryId") Long categoryId);

	
	// Sử dụng @Query
	@Query("SELECT i FROM Item i JOIN FETCH i.subcategory WHERE i.subcategory.id = :subcategoryId")
	List<Item> findBySubcategoryId(@Param("subcategoryId") Long subcategoryId);

	// Tìm các sản phẩm có số lượng tồn kho thấp hơn ngưỡng
	@Query("SELECT i FROM Item i WHERE i.stockQuantity < :threshold")
	List<Item> findByStockQuantityLessThan(@Param("threshold") int threshold);

	// Tìm các sản phẩm có số lượng đã bán cao hơn ngưỡng
	@Query("SELECT i FROM Item i WHERE i.soldQuantity > :threshold")
	List<Item> findBySoldQuantityGreaterThan(@Param("threshold") int threshold);

} 