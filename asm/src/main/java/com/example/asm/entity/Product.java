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
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String image;
    Double price;

    @Column(name = "Description", columnDefinition = "nvarchar(MAX)")
    String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();

    Boolean available;

    @ManyToOne
    @JoinColumn(name = "Categoryid")
    Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Favorite> favorites;

    // --- CÁC TRƯỜNG TRANSIENT (Dùng cho giỏ hàng, không lưu vào bảng Products) ---
    @Transient
    Integer quantity = 1;

    @Transient
    String size = "";  // [MỚI] Lưu size tạm thời trong giỏ

    @Transient
    String color = ""; // [MỚI] Lưu màu tạm thời trong giỏ
    // -----------------------------------------------------------------------------

    public Integer getAvgStars() {
        if (this.reviews == null || this.reviews.isEmpty()) return 0;
        int total = 0;
        for (Review r : this.reviews) total += r.getRating();
        return total / this.reviews.size();
    }
}