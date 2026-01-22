package com.example.asm.dao;

import com.example.asm.entity.Product;
import com.example.asm.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReportDAO extends JpaRepository<Product, Long> {
    // SỬA: Dùng "new com.example.asm.entity.Report" thay vì "new Report"
    @Query("SELECT new com.example.asm.entity.Report(o.category, sum(o.price), count(o)) " +
            " FROM Product o " +
            " GROUP BY o.category" +
            " ORDER BY sum(o.price) DESC")
    List<Report> getInventory();
}