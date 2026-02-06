package com.example.asm.entity;

import java.io.Serializable;

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
@Table(name = "OrderDetails")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double price;
    Integer quantity;

    // --- [MỚI] THÊM 2 TRƯỜNG NÀY ĐỂ LƯU SIZE & MÀU ---
    @Column(name = "Size")
    String size;

    @Column(name = "Color")
    String color;
    // -------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "Productid")
    Product product;

    @ManyToOne
    @JoinColumn(name = "Orderid")
    Order order;
}