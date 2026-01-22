package com.example.asm.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
    private Serializable group; // Nhóm (Category)
    private Double sum;         // Tổng doanh thu
    private Long count;         // Số lượng bán ra
}