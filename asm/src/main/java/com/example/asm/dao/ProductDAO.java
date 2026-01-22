package com.example.asm.dao;

import com.example.asm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// LƯU Ý QUAN TRỌNG:
// Đổi "Long" thành "Integer" để khớp với file Product.java (ID của bạn là Integer)
public interface ProductDAO extends JpaRepository<Product, Integer> {

    // 1. Tìm theo danh mục (Sửa tham số thành Integer cho đồng bộ)
    @Query("SELECT p FROM Product p WHERE p.category.id=?1")
    List<Product> findByCategoryId(Integer cid);

    // 2. Tìm kiếm thủ công (Code cũ của bạn giữ nguyên)
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
    List<Product> findByKeywords(String keywords);

    // 3. --- HÀM MỚI DÀNH RIÊNG CHO CHATBOT ---
    // Spring sẽ tự động hiểu: Tìm sản phẩm có tên CHỨA từ khóa này
    // Không cần viết câu lệnh SELECT phức tạp
    List<Product> findByNameContaining(String name);
}