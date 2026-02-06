package com.example.asm.interceptor;

import com.example.asm.dao.CategoryDAO;
import com.example.asm.service.CartService; // 1. Import CartService
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryDAO dao;

    @Autowired
    CartService cart; // 2. Tiêm CartService vào đây

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Chỉ chạy khi view hợp lệ (để tránh lỗi khi gọi API hoặc file tĩnh)
        if(request.getAttribute("cates") == null && modelAndView != null && !modelAndView.isEmpty()) {

            // A. Đưa danh mục ra menu (Code cũ của bạn)
            request.setAttribute("cates", dao.findAll());

            // B. [QUAN TRỌNG] Đưa Giỏ hàng vào Session để giao diện hiển thị số lượng
            // Dòng này giúp HTML gọi được ${session.cart.count}
            request.getSession().setAttribute("cart", cart);
        }
    }
}