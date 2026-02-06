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

    Map<Integer, Product> map = new HashMap<>();

    @Override
    public void add(Integer id, String size, String color) {
        Product p = map.get(id);
        if (p == null) {
            // Nếu chưa có trong giỏ: Lấy từ DB và set thông tin mới
            p = dao.findById(id).get();
            p.setQuantity(1);
            p.setSize(size != null ? size : "");
            p.setColor(color != null ? color : "");
            map.put(id, p);
        } else {
            // Nếu đã có: Tăng số lượng
            p.setQuantity(p.getQuantity() + 1);

            // Cập nhật lại size/màu mới nhất khách vừa chọn (Optional)
            if (size != null && !size.isEmpty()) p.setSize(size);
            if (color != null && !color.isEmpty()) p.setColor(color);
        }
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public void update(Integer id, Integer qty) {
        Product p = map.get(id);
        if (p != null) {
            p.setQuantity(qty);
        }
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
                .mapToInt(Product::getQuantity)
                .sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
    }
}