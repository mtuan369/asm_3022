package com.example.asm.dao;

import com.example.asm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long> {

    // 1. Tìm đơn hàng theo Username (Dùng cho lịch sử mua hàng)
    @Query("SELECT o FROM Order o WHERE o.account.username = ?1")
    List<Order> findByUsername(String username);

    // 2. Thống kê doanh thu (ĐÃ SỬA LỖI TÊN ENTITY)
    // Lưu ý: Đổi "OrderDetails" thành "OrderDetail" (bỏ chữ s)
    @Query("SELECT YEAR(o.createDate), MONTH(o.createDate), SUM(d.price * d.quantity) " +
            "FROM OrderDetail d JOIN d.order o " +
            "GROUP BY YEAR(o.createDate), MONTH(o.createDate) " +
            "ORDER BY YEAR(o.createDate) DESC, MONTH(o.createDate) DESC")
    List<Object[]> getRevenueByMonth();
}