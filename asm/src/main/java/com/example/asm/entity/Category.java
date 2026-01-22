package com.example.asm.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter // <--- Thêm cái này
@Setter // <--- Thêm cái này
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // Lưu ý: Nếu DB của bạn để ID là String thì sửa thành String

    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Product> products;
}