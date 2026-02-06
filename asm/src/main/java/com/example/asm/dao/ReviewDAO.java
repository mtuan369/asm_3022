package com.example.asm.dao;

import com.example.asm.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReviewDAO extends JpaRepository<Review, Long> {
    // Lấy đánh giá của 1 sản phẩm cụ thể
    @Query("SELECT r FROM Review r WHERE r.product.id = ?1 ORDER BY r.reviewDate DESC")
    List<Review> findByProductId(Integer productId);
}