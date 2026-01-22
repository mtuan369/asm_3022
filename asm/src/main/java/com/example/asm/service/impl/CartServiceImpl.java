package com.example.asm.service.impl;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import com.example.asm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductDAO dao;

    // 1. Sửa Key của Map: Long -> Integer
    Map<Integer, Product> map = new HashMap<>();

    // 2. Sửa tham số Long -> Integer
    @Override
    public void add(Integer id) {
        Product p = map.get(id);
        if (p == null) {
            p = dao.findById(id).get();
            p.setQuantity(1);
            map.put(id, p);
        } else {
            p.setQuantity(p.getQuantity() + 1);
        }
    }

    // 3. Sửa tham số Long -> Integer
    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    // 4. Sửa tham số Long -> Integer
    @Override
    public Product update(Integer id, int qty) {
        Product p = map.get(id);
        p.setQuantity(qty);
        return p;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Product> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        return map.values().stream()
                .mapToInt(item -> item.getQuantity())
                .sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}