package com.example.asm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();

    // --- MỚI: Thêm trạng thái đơn hàng ---
    Integer status = 0;

    @ManyToOne
    @JoinColumn(name = "Username")
    Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

    // --- MỚI: Hàm lấy tên trạng thái hiển thị lên View ---
    public String getStatusName() {
        if (this.status == 0) return "Chờ xác nhận";
        if (this.status == 1) return "Đang giao hàng";
        if (this.status == 2) return "Đã giao";
        if (this.status == 3) return "Đã hủy";
        return "Mới";
    }
}