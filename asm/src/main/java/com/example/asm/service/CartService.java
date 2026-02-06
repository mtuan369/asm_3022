package com.example.asm.service;

import com.example.asm.entity.Product;
import java.util.Collection;

public interface CartService {
    // Sửa dòng này: Thêm tham số size và color để khớp với Impl
    void add(Integer id, String size, String color);

    void remove(Integer id);

    void update(Integer id, Integer qty);

    void clear();

    Collection<Product> getItems();

    int getCount();

    double getAmount();
}