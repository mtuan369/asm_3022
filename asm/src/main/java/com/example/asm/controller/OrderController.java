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
    @Autowired CartService cart;
    @Autowired SessionService session;
    @Autowired OrderDAO orderDao;
    @Autowired OrderDetailDAO orderDetailDao;

    @GetMapping("/order/checkout")
    public String checkout(Model model) {
        Account user = session.get("user");
        if(user == null) return "redirect:/auth/login";
        if(cart.getCount() == 0) return "redirect:/cart/view";

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "order/checkout";
    }

    @PostMapping("/order/checkout")
    public String checkout(Model model, @RequestParam("address") String address) {
        Account user = session.get("user");
        Order order = new Order();
        order.setAccount(user);
        order.setAddress(address);
        order.setCreateDate(new Date());
        order.setStatus(0); // 0: Mới đặt (Chờ xác nhận)
        orderDao.save(order);

        for(Product p : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(p);
            detail.setPrice(p.getPrice());
            detail.setQuantity(p.getQuantity());
            // Lưu thêm Size/Color nếu có (cần cập nhật CartService trước)
            // detail.setSize(p.getSize());
            // detail.setColor(p.getColor());
            orderDetailDao.save(detail);
        }
        cart.clear();
        return "redirect:/order/detail/" + order.getId();
    }

    @GetMapping("/order/list")
    public String list(Model model) {
        Account user = session.get("user");
        // Lấy danh sách đơn hàng của user đó
        List<Order> orders = orderDao.findByUsername(user.getUsername());
        // Sắp xếp mới nhất lên đầu (Java Stream sort)
        orders.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));

        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Order order = orderDao.findById(id).get();
        model.addAttribute("order", order);
        return "order/detail";
    }

    // --- HỦY ĐƠN HÀNG (Chỉ khi Status = 0) ---
    @GetMapping("/order/cancel/{id}")
    public String cancel(@PathVariable("id") Long id) {
        Account user = session.get("user");
        Order order = orderDao.findById(id).orElse(null);

        if(user != null && order != null) {
            if(order.getAccount().getUsername().equals(user.getUsername()) && order.getStatus() == 0) {
                order.setStatus(3); // 3: Đã hủy
                orderDao.save(order);
            }
        }
        return "redirect:/order/list";
    }

    // --- [MỚI] KHÁCH XÁC NHẬN ĐÃ NHẬN HÀNG (Chỉ khi Status = 4) ---
    @GetMapping("/order/confirm-receipt/{id}")
    public String confirmReceipt(@PathVariable("id") Long id) {
        Account user = session.get("user");
        Order order = orderDao.findById(id).orElse(null);

        if(user != null && order != null) {
            // Kiểm tra đúng chủ sở hữu VÀ Đơn đang ở trạng thái 4 (Đã giao)
            if(order.getAccount().getUsername().equals(user.getUsername()) && order.getStatus() == 4) {
                order.setStatus(2); // 2: Hoàn tất thành công
                orderDao.save(order);
            }
        }
        return "redirect:/order/list";
    }
}