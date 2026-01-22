package com.example.asm.service;

import com.example.asm.entity.Product;
import java.util.Collection;

public interface CartService {
    // Sửa tất cả Long -> Integer
    void add(Integer id);
    void remove(Integer id);
    Product update(Integer id, int qty);
    void clear();
    Collection<Product> getItems();
    int getCount();
    double getAmount();
}