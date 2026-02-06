package com.example.asm.dao;

import com.example.asm.entity.Product;
import com.example.asm.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    // ... Giữ nguyên các hàm tìm kiếm khác của bạn ...
    @Query("SELECT p FROM Product p WHERE p.category.id=?1")
    Page<Product> findByCategoryId(Integer cid, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
    Page<Product> findByKeywords(String keywords, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);

    List<Product> findByNameContaining(String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.id <> ?2")
    List<Product> findRelatedProducts(Integer categoryId, Integer productId, Pageable pageable);

    @Query("SELECT o.category AS group, sum(o.price) AS sum, count(o) AS count " +
            " FROM Product o " +
            " GROUP BY o.category " +
            " ORDER BY sum(o.price) DESC")
    List<Report> getInventory();
}