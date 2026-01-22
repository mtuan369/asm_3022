package com.example.asm.controller;

import com.example.asm.dao.OrderDAO;
import com.example.asm.dao.OrderDetailDAO;
import com.example.asm.entity.Account;
import com.example.asm.entity.Order;
import com.example.asm.entity.OrderDetail;
import com.example.asm.entity.Product;
import com.example.asm.service.CartService;
import com.example.asm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    CartService cart;

    @Autowired
    SessionService session;

    @Autowired
    OrderDAO orderDao;

    @Autowired
    OrderDetailDAO orderDetailDao;

    // 1. Hiển thị trang thanh toán
    @GetMapping("/order/checkout")
    public String checkout(Model model) {
        Account user = session.get("user");
        if(user == null) {
            return "redirect:/auth/login";
        }

        if(cart.getCount() == 0) {
            return "redirect:/cart/view";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "order/checkout";
    }

    // 2. Thực hiện đặt hàng
    @PostMapping("/order/checkout")
    public String checkout(Model model, @RequestParam("address") String address) {
        Account user = session.get("user");

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setAccount(user);
        order.setAddress(address);
        order.setCreateDate(new Date());
        orderDao.save(order);

        // Lưu chi tiết đơn hàng (lấy từ giỏ hàng)
        for(Product p : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(p);
            detail.setPrice(p.getPrice());
            detail.setQuantity(p.getQuantity());
            orderDetailDao.save(detail);
        }

        // Xóa giỏ hàng sau khi đặt thành công
        cart.clear();

        return "redirect:/order/detail/" + order.getId();
    }

    // 3. Xem danh sách đơn hàng đã mua (Lịch sử)
    @GetMapping("/order/list")
    public String list(Model model) {
        Account user = session.get("user");
        List<Order> orders = orderDao.findByUsername(user.getUsername());
        model.addAttribute("orders", orders);
        return "order/list";
    }

    // 4. Xem chi tiết một đơn hàng cụ thể
    @GetMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Order order = orderDao.findById(id).get();
        model.addAttribute("order", order);
        return "order/detail";
    }
}