package com.example.asm.dao;

import com.example.asm.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FavoriteDAO extends JpaRepository<Favorite, Long> {
    // Lấy danh sách yêu thích của user
    @Query("SELECT f FROM Favorite f WHERE f.account.username = ?1")
    List<Favorite> findByUsername(String username);

    // Tìm xem user đã like sản phẩm này chưa
    @Query("SELECT f FROM Favorite f WHERE f.account.username = ?1 AND f.product.id = ?2")
    Favorite findByUserAndProduct(String username, Integer productId);
}