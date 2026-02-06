package com.example.asm.entity;

import java.io.Serializable;

// Interface Projection (Không dùng @Data, Không dùng Class)
public interface Report {
    Serializable getGroup(); // Hàm này sẽ hứng dữ liệu Category
    Double getSum();         // Hứng tổng tiền
    Long getCount();         // Hứng số lượng
}